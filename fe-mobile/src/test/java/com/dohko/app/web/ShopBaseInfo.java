package com.dohko.app.web;

import com.dohko.app.boot.WebTestApplication;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by xiangbin on 2016/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ShopBaseInfo {

    private static ApplicationContext ctx;

    @Before
    public void before() throws Exception {
        ctx = SpringApplication.run(WebTestApplication.class); //启动服务器 加载Config指定的组件
    }

    @Test
    public void baseInfoAdd() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity("http://127.0.0.1:8088/shop/info/add.htm", String.class);
//
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpHost httpHost = new HttpHost("127.0.0.1", 8088);
//        HttpRequest httpRequest = new HttpGet("/shop/add.htm");
//        CloseableHttpResponse httpRes = httpClient.execute(httpHost, httpRequest);
//        String jsondata = EntityUtils.toString(httpRes.getEntity());
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
