package com.dohko.core.web;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.config.WebConfig;
import com.dohko.core.services.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiangbin on 2016/7/5.
 */
@RestController

public class BenchmarkController extends BaseController {

    @Autowired
    private WebConfig webConfig;
    @RequestMapping("/benchmark")
    public ResultInfo benchmark(final @RequestParam int max, final @RequestParam int per, final @RequestParam String service) throws InterruptedException {
        if (!webConfig.isBenchmark()) {

        }
        DataMap dataMap = DataMap.Builder.create();
        long startTime = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(max);
        final Holder holder = getRequestHolder();
        for (int i = 0; i < max; i ++) {
            es.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < per; i++) {
                        DataMap retDs = Client.Builder.execute(service, DataMap.Builder.create(), holder);
                    }
                }
            });
        }
        es.shutdown();
        if (es.awaitTermination(3000000, TimeUnit.SECONDS)) {
            dataMap.put("totalCost", (System.currentTimeMillis()-startTime) + "ms");
            dataMap.put("threadMax", max);
            dataMap.put("threadPer", per);
        }
        return dataMap;
    }
}
