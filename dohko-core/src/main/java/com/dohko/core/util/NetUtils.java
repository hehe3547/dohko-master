package com.dohko.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangbin on 2016/6/14.
 */
public class NetUtils {
    private static Logger logger = LoggerFactory.getLogger(NetUtils.class);
    private static Map<String, String> cache = new HashMap<>();

    private static String HOST_ADDRESS = "_HOST_ADDRESS_";

    static {
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            cache.put(HOST_ADDRESS, localAddress.getHostAddress());
        } catch (Throwable e) {
            logger.warn("Failed to retriving local address by hostname:" + e);
        }
    }
    public static String getHostAddress() {
        return cache.get(HOST_ADDRESS);
    }
}
