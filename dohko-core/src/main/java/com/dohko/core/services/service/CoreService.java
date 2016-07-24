package com.dohko.core.services.service;

import com.dohko.core.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiangbin on 2016/7/5.
 */
@Component
public class CoreService {
    @Autowired
    ServerConfig serverConfig;



}
