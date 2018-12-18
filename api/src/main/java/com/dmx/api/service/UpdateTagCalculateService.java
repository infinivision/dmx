package com.dmx.api.service;

import avro.shaded.com.google.common.collect.Lists;
import com.dmx.api.ApiApplication;
import com.dmx.api.entity.meta.TTagMetaEntity;
import com.dmx.api.service.util.ClusterClientFactory;
import com.dmx.api.service.util.LauncherOptionParser;
import com.dmx.api.service.util.LauncherOptions;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class UpdateTagCalculateService {
    @Autowired
    private Environment env;

    private static final Logger logger = LogManager.getLogger(ApiApplication.class);

    @Scheduled(fixedRate = 100000000)
    public void UpdateTagsRules() {
        System.out.print(" Running job ........................................ \r\n");
        runJob();
    }

    private void runJob() {
        String [] args = {
                "-sql", "/Users/zhouchangyue/Downloads/flink/sql.txt",
                "-mode", "standalone",
                "-name", "test_flink",
                "-localSqlPluginPath", "/Users/zhouchangyue/Downloads/flink/plugins",
                "-remoteSqlPluginPath", "/opt/plugins",
                "-confProp", "{\"time.characteristic\": \"EventTime\",\"sql.checkpoint.interval\": 10000}",
                "-flinkconf", "/Users/zhouchangyue/Downloads/flink"
        };

        try {
            LauncherOptionParser optionParser = new LauncherOptionParser(args);
            LauncherOptions launcherOptions = optionParser.getLauncherOptions();
            String mode = launcherOptions.getMode();
            List<String> argList = optionParser.getProgramExeArgList();

            String pluginRoot = launcherOptions.getLocalSqlPluginPath();
            File jarFile = new File(pluginRoot + "/core.jar");
            String[] remoteArgs = argList.toArray(new String[argList.size()]);
            PackagedProgram program = new PackagedProgram(jarFile, Lists.newArrayList(), remoteArgs);

            if(StringUtils.isNotBlank(launcherOptions.getSavePointPath())){
                program.setSavepointRestoreSettings(SavepointRestoreSettings.forPath(launcherOptions.getSavePointPath(), BooleanUtils.toBoolean(launcherOptions.getAllowNonRestoredState())));
            }

            ClusterClient clusterClient = ClusterClientFactory.createClusterClient(launcherOptions);
            /*
            pluginRoot = launcherOptions.getLocalSqlPluginPath();
            jarFile = new File("");
            remoteArgs = argList.toArray(new String[argList.size()]);
            program = new PackagedProgram(jarFile, Lists.newArrayList(), remoteArgs);
            if(StringUtils.isNotBlank(launcherOptions.getSavePointPath())){
                program.setSavepointRestoreSettings(SavepointRestoreSettings.forPath(launcherOptions.getSavePointPath(), BooleanUtils.toBoolean(launcherOptions.getAllowNonRestoredState())));
            }
            */
            //final JobGraph jobGraph;
            //jobGraph = PackagedProgramUtils.createJobGraph(program, new Configuration(), 1);
            //clusterClient.runDetached(jobGraph,null);
            clusterClient.run(program, 1);
            clusterClient.shutdown();
        } catch (Exception e) {
            System.out.print(".................. Exception: " + e.getMessage() + "\r\n");
            e.printStackTrace();
        }
    }
}
