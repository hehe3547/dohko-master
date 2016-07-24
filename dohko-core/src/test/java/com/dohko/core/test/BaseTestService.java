package com.dohko.core.test;

import com.dohko.core.base.DataMap;
import com.dohko.core.services.Client;
import com.dohko.core.util.LogUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiangbin on 2016/6/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(NativeTestApplication.class)
@SpringApplicationConfiguration(ServerTestApplication.class)
public class BaseTestService {

    private Logger logger = LoggerFactory.getLogger(BaseTestService.class);
    @Test
    public void execute() {
        DataMap retDs = Client.Builder.execute("example_helloWorld", DataMap.Builder.create());
        LogUtils.info(retDs, logger);
    }
}
