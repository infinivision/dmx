package com.dmx.api.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TCategoryRepository;
import com.dmx.api.dao.meta.TDashboardChartRepository;
import com.dmx.api.entity.meta.TCategoryEntity;
import com.dmx.api.entity.meta.TDashboardChartEntity;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TDashboardChartAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TDashboardChartRepository tDashboardChartRepository;

    private String name = "test";
    private String rules = "";
    private Long updateTime = System.currentTimeMillis() / 1000;
    private Long createTime = System.currentTimeMillis() / 1000;
    private String createUserName = "admin";
    private String createGroupName = "group";
    private Integer platformId = 1;
    private String platformName = "infinivision";

    @Test
    public void test01() throws Exception {
        TDashboardChartEntity bean = new TDashboardChartEntity();

        bean.setName(name);
        bean.setRules(rules);
        bean.setUpdateTime(updateTime);
        bean.setCreateTime(createTime);
        bean.setCreateUserName(createUserName);
        bean.setCreateGroupName(createGroupName);
        bean.setPlatformId(platformId);
        bean.setPlatformName(platformName);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TDashboardChartEntity> req_entity = new HttpEntity<TDashboardChartEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/dashboard", HttpMethod.POST, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test02() throws Exception {
        TDashboardChartEntity item = tDashboardChartRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/dashboard" + "/" + item.getId(),
                HttpMethod.GET, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test03() throws Exception {
        TDashboardChartEntity bean = new TDashboardChartEntity();

        TDashboardChartEntity item = tDashboardChartRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TDashboardChartEntity> req_entity = new HttpEntity<TDashboardChartEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/dashboard" + "/" + item.getId(),
                HttpMethod.PUT, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test04() throws Exception {
        ResponseEntity<GetListMessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/dashboard" + "/list?page=0&size=10",
                HttpMethod.GET, null, GetListMessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        TDashboardChartEntity item = tDashboardChartRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/dashboard" + "/" + item.getId(),
                HttpMethod.DELETE, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }
}
