package com.dohko.core.registry;

/**
 * Created by xiangbin on 2016/6/14.
 */
public class Param {
    //分组
    final String group;
    final int port;
    //地址
    final String host;
    //interfaceName
    final String path;
    String data;

    public Param(String group, String host, int port, String path) {
        this.group = group;
        this.host = host;
        this.port = port;
        this.path = path;
    }

}
