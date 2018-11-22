package com.dmx.api.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TFunnelRepository;
import com.dmx.api.entity.meta.TFunnelEntity;
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
public class TFunnelAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TFunnelRepository tFunnelRepository;

    private String name = "test";
    private Integer step = 1;
    private Integer customerCount = 0;
    private String eventName = "";
    private Integer eventNameHash = 0;
    private Long updateTime = System.currentTimeMillis() / 1000;
    private Long createTime = System.currentTimeMillis() / 1000;
    private String createUserName = "admin";
    private String createGroupName = "group";
    private Integer platformId = 1;
    private String platformName = "infinivision";

    @Test
    public void test01() throws Exception {
        TFunnelEntity bean = new TFunnelEntity();

        bean.setName(name);
        bean.setStep(step);
        bean.setCustomerCount(customerCount);
        bean.setEventName(eventName);
        bean.setEventNameHash(eventNameHash);
        bean.setUpdateTime(updateTime);
        bean.setCreateTime(createTime);
        bean.setCreateUserName(createUserName);
        bean.setCreateGroupName(createGroupName);
        bean.setPlatformId(platformId);
        bean.setPlatformName(platformName);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TFunnelEntity> req_entity = new HttpEntity<TFunnelEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/funnel", HttpMethod.POST, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test02() throws Exception {
        TFunnelEntity item = tFunnelRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/funnel" + "/" + item.getId(),
                HttpMethod.GET, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test03() throws Exception {
        TFunnelEntity bean = new TFunnelEntity();

        bean.setEventName("index");

        TFunnelEntity item = tFunnelRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TFunnelEntity> req_entity = new HttpEntity<TFunnelEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/funnel" + "/" + item.getId(),
                HttpMethod.PUT, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test04() throws Exception {
        ResponseEntity<GetListMessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/funnel" + "/list?page=0&size=10",
                HttpMethod.GET, null, GetListMessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        TFunnelEntity item = tFunnelRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/funnel" + "/" + item.getId(),
                HttpMethod.DELETE, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }
}
