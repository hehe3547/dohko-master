package com.dohko.app.web;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by xiangbin on 2016/7/13.
 */
public class ConnectionTest {

    public void testConnectEncoding() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost httpHost = new HttpHost("127.0.0.1", 8089);
        HttpRequest httpRequest = new HttpGet("/shop/add.htm");
        CloseableHttpResponse httpRes = httpClient.execute(httpHost, httpRequest);
        String jsondata = EntityUtils.toString(httpRes.getEntity());
        System.out.println(jsondata);
    }

}
