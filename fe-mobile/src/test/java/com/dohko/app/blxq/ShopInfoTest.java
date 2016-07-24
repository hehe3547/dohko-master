package com.dohko.app.blxq;

import com.dohko.app.boot.NativeTestApplication;
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
 * Created by xiangbin on 2016/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(NativeTestApplication.class)
public class ShopInfoTest {

    private Logger logger = LoggerFactory.getLogger(ShopInfoTest.class);
    @Test
    public void shopGoodInfoQuery() {
        DataMap params = DataMap.Builder.create().put("shopID", "8");
        DataMap retDs = Client.Builder.execute("shop_queryShopGoodInfo", params);
        LogUtils.info(retDs, logger);
    }
}
