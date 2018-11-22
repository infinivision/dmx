package com.dmx.api.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TFunnelRepository;
import com.dmx.api.dao.meta.TJourneyRepository;
import com.dmx.api.entity.meta.TFunnelEntity;
import com.dmx.api.entity.meta.TJourneyEntity;
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
public class TJourneyAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TJourneyRepository tJourneyRepository;

    private String name = "test";
    private Integer step = 1;
    private String eventName = "test";
    private Integer eventNameHash = 0;
    private Integer customerCount = 0;

    private Long updateTime = System.currentTimeMillis() / 1000;
    private Long createTime = System.currentTimeMillis() / 1000;
    private String createUserName = "admin";
    private String createGroupName = "group";
    private Integer platformId = 1;
    private String platformName = "infinivision";

    @Test
    public void test01() throws Exception {
        TJourneyEntity bean = new TJourneyEntity();

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
        HttpEntity<TJourneyEntity> req_entity = new HttpEntity<TJourneyEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/journey", HttpMethod.POST, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test02() throws Exception {
        TJourneyEntity item = tJourneyRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/journey" + "/" + item.getId(),
                HttpMethod.GET, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test03() throws Exception {
        TJourneyEntity bean = new TJourneyEntity();

        bean.setEventName("index");

        TJourneyEntity item = tJourneyRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TJourneyEntity> req_entity = new HttpEntity<TJourneyEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/journey" + "/" + item.getId(),
                HttpMethod.PUT, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test04() throws Exception {
        ResponseEntity<GetListMessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/journey" + "/list?page=0&size=10",
                HttpMethod.GET, null, GetListMessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        TJourneyEntity item = tJourneyRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/journey" + "/" + item.getId(),
                HttpMethod.DELETE, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }
}
