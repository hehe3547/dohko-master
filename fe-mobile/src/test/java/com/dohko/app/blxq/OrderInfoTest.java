package com.dohko.app.blxq;

import com.dohko.app.boot.NativeTestApplication;
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.services.Client;
import com.dohko.core.util.LogUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiangbin on 2016/7/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(NativeTestApplication.class)
public class OrderInfoTest {
    private Logger logger = LoggerFactory.getLogger(ShopInfoTest.class);
    public void createOrderInfo() {
        DataMap params = DataMap.Builder.create().put("shopID", "8");
        DataList dataList = DataList.Builder.create();
        dataList.add(DataMap.Builder.create().put("goodCode", "123").put("number", 2))
                .add(DataMap.Builder.create().put("goodCode", "124").put("number", 1));
        params.put("detailList", dataList);
        DataMap retDs = Client.Builder.execute("order_createOrderInfo", params);
        LogUtils.info(retDs, logger);
    }
}
