package com.dohko.app.web.client;

import com.dohko.app.utils.HttpUtils;
import com.dohko.core.base.DataMap;

import java.io.IOException;

/**
 * Created by xiangbin on 2016/7/17.
 */
public class ShopGoogTest {

    private String host = "127.0.0.1";
    private int port = 8089;

    public void shopIndex() throws IOException {
        DataMap dataMap = DataMap.Builder.create();
        dataMap.put("categoryCode", "123");
        dataMap.put("categoryName", "啤酒");
        dataMap.put("shopID", 8);
        String jsondata = HttpUtils.httpPost(host, port, "/shop/category/add.htm", dataMap);
        System.out.println(jsondata);
    }
}
