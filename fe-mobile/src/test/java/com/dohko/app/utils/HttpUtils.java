package com.dohko.app.utils;


import com.dohko.core.base.DataMap;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangbin on 2016/7/17.
 */
public class HttpUtils {


    public static String httpPost(String host, int port, String uri, DataMap dataMap) throws IOException {
        HttpPost httppost = new HttpPost(uri);
        List<NameValuePair> params = new ArrayList();
        for (String key : dataMap.keySet()) {
            params.add(new BasicNameValuePair(key, dataMap.getString(key)));
        }
        UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(params, "UTF-8");
        httppost.setEntity(urlEntity);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost httpHost = new HttpHost(host, port);
        CloseableHttpResponse httpRes = httpClient.execute(httpHost, httppost);
        String jsondata = EntityUtils.toString(httpRes.getEntity());
        return jsondata;
    }

}
