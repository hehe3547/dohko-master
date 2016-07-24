package com.dohko.app.web.shop;

import com.dohko.app.utils.HttpUtils;
import com.dohko.core.base.DataMap;

import java.io.IOException;

/**
 * Created by xiangbin on 2016/7/17.
 */
public class GoodInfoTest {

    private String host = "127.0.0.1";
    private int port = 8089;


    public void addCategory() throws IOException {
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put("categoryCode", "123");
        dataMap.put("categoryName", "啤酒");
        dataMap.put("shopID", 8);
        String jsondata = HttpUtils.httpPost(host, port, "/shop/category/add.htm", dataMap);
        System.out.println(jsondata);
    }

    public void addGood() throws IOException {
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put("goodCode", "126");
        dataMap.put("goodName", "雪花啤酒(48)");
        dataMap.put("shopID", 8);
        dataMap.put("categoryCode", "123");
        dataMap.put("price", "50");
        dataMap.put("prePrice", "46");
        String jsondata = HttpUtils.httpPost(host, port, "/shop/good/add", dataMap);
        System.out.println(jsondata);
    }
}
