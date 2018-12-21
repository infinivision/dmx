package com.dmx.api.service;

import avro.shaded.com.google.common.collect.Lists;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dmx.api.ApiApplication;
import com.dmx.api.dao.meta.TTagMetaRepository;
import com.dmx.api.dao.meta.TTagTaskRepository;
import com.dmx.api.entity.meta.TTagMetaEntity;
import com.dmx.api.entity.meta.TTagTaskEntity;
import com.dmx.api.service.util.ClusterClientFactory;
import com.dmx.api.service.util.LauncherOptionParser;
import com.dmx.api.service.util.LauncherOptions;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.JobSubmissionResult;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.hadoop.shaded.com.google.common.base.Charsets;
import org.apache.flink.runtime.client.JobStatusMessage;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateTagCalculateService {
    @Autowired
    private Environment env;

    @Autowired
    TTagMetaRepository tTagMetaRepository;

    @Autowired
    TTagTaskRepository tTagTaskRepository;

    private static final Logger logger = LogManager.getLogger(ApiApplication.class);

    @Scheduled(fixedRate = 100000000)
    public void UpdateTagsRules() {
        System.out.print(" Running job ........................................ \r\n");

        Integer group = Integer.parseInt(env.getProperty("flink.job.group.num"));
        if (0 >= group) {
            logger.error("group is zero ...............");

            return;
        }

        System.out.print(" group:" + group + " ........................................ \r\n");

        List<TTagMetaEntity> tag_meta_list = tTagMetaRepository.findByIsSystemAndIsStaticAndType(0, 0, 1);

        if (0 >= tag_meta_list.size()) {
            logger.debug("*********** has no tag needed to calculate");

            return;
        }

        System.out.print(" tag size :" + tag_meta_list.size() + " ........................................ \r\n");

        ClusterClient clusterClient = createClusterClient();
        if (null == clusterClient) {
            logger.error("create cluster client failed ........");

            return;
        }

        System.out.print(" cluster connected success ........................................ \r\n");

        ArrayList<List<TTagMetaEntity>> group_list = hashTagEntity(group, tag_meta_list);
        List<TTagTaskEntity> task_lists = tTagTaskRepository.findAll();

        if (0 >= task_lists.size()) {
            if (!runJobs(clusterClient, group, group_list)) {
                logger.error("run jobs failed ...............................");
            }

            return;
        }

        System.out.print(" is not first running job task size: " + task_lists.size() + " ............... \r\n");

        Integer tag_list_is_zero_count = getTagListIsZero( group_list);
        Integer alive_jobs_count = group - tag_list_is_zero_count;

        if (alive_jobs_count != task_lists.size()) {
            System.out.print(" alive group count: " + alive_jobs_count + " is not equal task size: " + task_lists.size() +  " ... \r\n");

            if (!killJobs(clusterClient, task_lists)) {
                logger.error("kill jobs failed ...............................");

                return;
            }
        } else {
            System.out.print(" alive group count: " + alive_jobs_count + " is equal task size: " + task_lists.size() +  " ... \r\n");
        }

        for (TTagTaskEntity item: task_lists) {
            Integer group_id = getTaskGroupId(item);

            if (0 >= group_id) {
                logger.error("get job group id failed job name:" + item.getJobName() + " .....................");

                return;
            }

            System.out.print(" check group: " + group_id + " job name: " + item.getJobName() + "...... \r\n");

            if (!isJobRunning(clusterClient, item) && 0 < group_list.get(group_id-1).size()) {
                System.out.print("group: " + group_id + " job name: " + item.getJobName() + " is not running ... \r\n");

                deleteJob(item);

                if (!runJob(clusterClient, group_id, group_list.get(group_id-1))) {
                    logger.error("get group id: " + group_id + " failed .....................");

                    return;
                }

                continue;
            }

            if (isNeedRunJob(item, group_list.get(group_id-1)) && 0 < group_list.get(group_id-1).size()) {
                System.out.print("group: " + group_id + " job name: " + item.getJobName() + " need rerunning ... \r\n");

                if (!killJob(clusterClient, item)) {
                    logger.error("kill job name: " + item.getJobName() + " failed .....................");

                    return;
                }

                if (!runJob(clusterClient, group_id, group_list.get(group_id-1))) {
                    logger.error("get group id: " + group_id + " failed .....................");

                    return;
                }
            }

            System.out.print(" check group: " + group_id + " job name: " + item.getJobName() + " success \r\n");
        }

        logger.debug("running job success ..................");
    }

    private ClusterClient createClusterClient() {
        String [] launcher_string = {
                "-sql", env.getProperty("flink.job.sql.path") + "/" + ".tmp.txt",
                "-name", "test",
                "-localSqlPluginPath", env.getProperty("flink.job.local.sql.plugins"),
                "-remoteSqlPluginPath", env.getProperty("flink.job.remote.sql.plugins"),
                "-mode", env.getProperty("flink.cluster.mode"),
                "-flinkconf", env.getProperty("flink.cluster.conf"),
                "-yarnconf", env.getProperty("flink.cluster.yarn.conf")
        };

        LauncherOptionParser optionParser = new LauncherOptionParser(launcher_string);
        LauncherOptions launcherOptions = optionParser.getLauncherOptions();

        try {
            return ClusterClientFactory.createClusterClient(launcherOptions);
        } catch (Exception e) {
            logger.error("create cluster client failed:" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private Boolean isJobRunning(ClusterClient cluster_client, TTagTaskEntity task) {
        try {
            CompletableFuture<Collection<JobStatusMessage>> jobs_result = cluster_client.listJobs();

            JobID job_id = JobID.fromByteArray(task.getJobId());
            Iterator<JobStatusMessage> message_list = jobs_result.get().iterator();

            while (message_list.hasNext()) {
                JobStatusMessage message = message_list.next();

                if (!message.getJobId().equals(job_id)) {
                    continue;
                }

                if (!message.getJobState().isTerminalState()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            logger.error("check job: " + task.getJobName() + " running failed:" + e.getMessage());
        }

        return false;
    }

    private Boolean runJobs(ClusterClient cluster_client, Integer group, ArrayList<List<TTagMetaEntity>> group_list) {
        for(int i = 1; i <= group; ++i) {
            if (!runJob(cluster_client, i, group_list.get(i-1))) {
                return false;
            }
        }

        return true;
    }

    private Boolean runJob(ClusterClient cluster_client,Integer group_id, List<TTagMetaEntity> tag_lists) {
        if (0 >= tag_lists.size()) {
            return true;
        }

        String job_name = env.getProperty("flink.job.name.prefix") + "_" + group_id + "_" + tag_lists.size();
        String local_sql_plugins = env.getProperty("flink.job.local.sql.plugins");
        String jar_file = env.getProperty("flink.job.jar.file");
        String remote_sql_plugins = env.getProperty("flink.job.remote.sql.plugins");
        String conf_prop = env.getProperty("flink.job.conf.prop");
        String savePointPath = env.getProperty("flink.job.save.path");
        String allow_non_restore_string = env.getProperty("flink.job.save.allow_non_restore");
        Boolean allow_non_restore = allow_non_restore_string.equalsIgnoreCase("true")?true:false;

        Integer parallel = Integer.parseInt(env.getProperty("flink.job.parallel.num"));

        String sql = getSql(tag_lists);

        PackagedProgram program = packagedProgram(job_name, sql, local_sql_plugins, jar_file, remote_sql_plugins, conf_prop, savePointPath, allow_non_restore);
        if (null == program) {
            return false;
        }

        try {
            JobSubmissionResult result = cluster_client.run(program, parallel);

            addJob(1, result.getJobID(), job_name,  tag_lists);
        } catch (Exception e) {
            logger.error("run job file:" + jar_file + " job_name:" + job_name + " failed:" + e.getMessage());
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private Boolean killJobs(ClusterClient clusterClient, List<TTagTaskEntity> tasks) {
        for (TTagTaskEntity item: tasks) {
            if (!killJob(clusterClient, item)) {
                return false;
            }
        }

        return true;
    }

    private Boolean killJob(ClusterClient clusterClient, TTagTaskEntity task) {
        JobID job_id = JobID.fromByteArray(task.getJobId());

        try {
            clusterClient.cancel(job_id);

            deleteJob(task);
        } catch (Exception e) {
            logger.error("kill job name:" + task.getJobName() + " failed:" + e.getMessage());
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private  String getSql(List<TTagMetaEntity> tag_lists) {
        String source_table_desc = env.getProperty("flink.streaming.source.table.desc");
        String source_table_name = env.getProperty("flink.streaming.source.table.name");
        String dest_table_desc = env.getProperty("flink.streaming.dest.table.desc");
        String dest_table_name = env.getProperty("flink.streaming.dest.table.name");
        String desc_columns = env.getProperty("flink.streaming.dest.table.columns");

        String sql = source_table_desc + ";\r\n" + dest_table_desc + ";\r\n";

        for (TTagMetaEntity item: tag_lists) {
            sql += "INSERT INTO " + dest_table_name + "\n SELECT " + desc_columns + " FROM " + source_table_name + "\n WHERE " + item.getRules() + ";\r\n";
        }

        System.out.print(" ******* sql:" + sql + "\r\n");

        try {
            return URLEncoder.encode(sql, Charsets.UTF_8.name());
        } catch (Exception e) {
            logger.error("encode sql:" + sql + " failed");

            e.printStackTrace();
        }

        return "";
    }

    private PackagedProgram packagedProgram(String job_name, String sql, String local_sql_plugins, String jar_file, String remote_sql_plugins, String conf_prop, String savePointPath, Boolean allowNonRestoredState) {
        String [] args = {
                "-sql", sql,
                "-name", job_name,
                "-localSqlPluginPath", local_sql_plugins,
                "-remoteSqlPluginPath", remote_sql_plugins,
                "-confProp", conf_prop
        };

        File jarFile = new File(local_sql_plugins + "/" + jar_file);

        try {
            PackagedProgram program = new PackagedProgram(jarFile, Lists.newArrayList(), args);

            if(StringUtils.isNotBlank(savePointPath)){
                program.setSavepointRestoreSettings(SavepointRestoreSettings.forPath(savePointPath, allowNonRestoredState));
            }

            return program;
        } catch (Exception e) {
            logger.error("run job:" + job_name + " failed:" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private Boolean addJob(Integer status, JobID job_id, String job_name, List<TTagMetaEntity> tag_lists) {
        TTagTaskEntity entity = new TTagTaskEntity();

        entity.setJobId(job_id.getBytes());
        entity.setJobName(job_name);
        entity.setStatus(status);

        Map<String, Integer> tags = new HashMap<String, Integer>();

        for (TTagMetaEntity item: tag_lists) {
            tags.put(item.getId(), item.getUpdateTime().intValue());
        }

        entity.setTagList(JSON.toJSONString(tags));

        entity.setCreateUserName("admin");
        entity.setCreateGroupName("group");
        entity.setPlatformId(1);
        entity.setPlatformName("infinivision");

        try {
            tTagTaskRepository.save(entity);
        } catch (Exception e) {
            logger.error("add job:" + job_name + " failed:" + e.getMessage());
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private void deleteJob(TTagTaskEntity task) {
        tTagTaskRepository.deleteById(task.getId());
    }

    private ArrayList<List<TTagMetaEntity>> hashTagEntity(Integer group, List<TTagMetaEntity> tag_lists) {
        ArrayList<List<TTagMetaEntity>> group_list = new ArrayList<List<TTagMetaEntity>>();

        for (int i = 1; i <= group; ++i) {
            List<TTagMetaEntity> item = new ArrayList<TTagMetaEntity>();

            group_list.add(item);
        }

        for (TTagMetaEntity item: tag_lists) {
            group_list.get(rotatingHash(item.getId(), 1773)%group).add(item);
        }

        return group_list;
    }

    private Boolean isNeedRunJob(TTagTaskEntity task, List<TTagMetaEntity> tag_lists) {

        Map<String, Integer> tags_update_time = new HashMap<String, Integer>();

        for (TTagMetaEntity item: tag_lists) {
            tags_update_time.put(item.getId(), item.getUpdateTime().intValue());
        }

        JSONObject json_tag_obj = JSONObject.parseObject(task.getTagList());

        Set<String> keys = json_tag_obj.keySet();

        if (keys.size() != tag_lists.size()) {
            return true;
        }

        for (String item: keys) {
            if (!json_tag_obj.getInteger(item).equals(tags_update_time.get(item))) {
                return true;
            }
        }

        return false;
    }

    private Integer getTagListIsZero(ArrayList<List<TTagMetaEntity>> group_list) {
        Integer count = 0;

        for (List<TTagMetaEntity> item: group_list) {
            if (0 >= item.size()) {
                ++count;
            }
        }

        return count;
    }

    private Integer getTaskGroupId(TTagTaskEntity task) {
        String [] array = task.getJobName().split("_");

        return Integer.parseInt(array[array.length-2]);
    }

    private int rotatingHash(String key, int prime) {
        int hash, i;
        for (hash=key.length(), i=0; i<key.length(); ++i)
            hash = (hash<<4)^(hash>>28)^key.charAt(i);
        return (hash % prime);
    }
}
