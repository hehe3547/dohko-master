package com.dohko.app.web.shop;

import com.dohko.app.utils.HttpUtils;
import com.dohko.core.base.DataMap;

import java.io.IOException;

/**
 * Created by xiangbin on 2016/7/15.
 */
public class ShopBaseInfo {


    private String host = "127.0.0.1";
    private int port = 8089;

    public void baseInfoAdd() throws IOException {
        String jsondata = HttpUtils.httpPost(host, port, "/shop/info/add.htm", DataMap.Builder.create());
        System.out.println(jsondata);
    }
}
