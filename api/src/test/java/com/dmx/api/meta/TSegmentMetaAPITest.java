package com.dmx.api.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TSegmentMetaRepository;
import com.dmx.api.entity.meta.TSegmentMetaEntity;
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
public class TSegmentMetaAPITest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TSegmentMetaRepository tSegmentMetaRepository;

    private String name = "test";
    private String category = "";
    private String rules = "";
    private Long customerCount = new Long(0);
    private String description = "desc";
    private Long updateTime = System.currentTimeMillis() / 1000;
    private Long createTime = System.currentTimeMillis() / 1000;
    private String createUserName = "admin";
    private String createGroupName = "group";
    private Integer platformId = 1;
    private String platformName = "infinivision";

    @Test
    public void test01() throws Exception {
        TSegmentMetaEntity bean = new TSegmentMetaEntity();

        bean.setName(name);
        bean.setCategory(category);
        bean.setRules(rules);
        bean.setCustomerCount(customerCount);
        bean.setDescription(description);
        bean.setUpdateTime(updateTime);
        bean.setCreateTime(createTime);
        bean.setCreateUserName(createUserName);
        bean.setCreateGroupName(createGroupName);
        bean.setPlatformId(platformId);
        bean.setPlatformName(platformName);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TSegmentMetaEntity> req_entity = new HttpEntity<TSegmentMetaEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/segment", HttpMethod.POST, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test02() throws Exception {
        TSegmentMetaEntity item = tSegmentMetaRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/segment" + "/" + item.getId(),
                HttpMethod.GET, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test03() throws Exception {
        TSegmentMetaEntity bean = new TSegmentMetaEntity();

        bean.setDescription(description + "_desc");

        TSegmentMetaEntity item = tSegmentMetaRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<TSegmentMetaEntity> req_entity = new HttpEntity<TSegmentMetaEntity>(bean, requestHeaders);

        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/segment" + "/" + item.getId(),
                HttpMethod.PUT, req_entity,  MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test04() throws Exception {
        ResponseEntity<GetListMessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/segment" + "/list?page=0&size=10",
                HttpMethod.GET, null, GetListMessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }

    @Test
    public void test99() throws Exception {
        TSegmentMetaEntity item = tSegmentMetaRepository.findByName(name);
        then(item).isNotEqualTo(null);
        then(item.getName()).isEqualTo(name);

        HttpHeaders requestHeaders = new HttpHeaders();
        ResponseEntity<MessageResponse> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + "/segment" + "/" + item.getId(),
                HttpMethod.DELETE, null, MessageResponse.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.hasBody()).isEqualTo(true);
        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("");
    }
}
