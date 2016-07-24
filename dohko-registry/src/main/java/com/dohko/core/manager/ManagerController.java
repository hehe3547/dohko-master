package com.dohko.core.manager;

/**
 * Created by xiangbin on 2016/6/15.
 */
import com.dohko.core.base.DataList;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.registry.RegistryManager;
import com.dohko.core.web.HtmController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/manager")
public class ManagerController extends HtmController {

    private Logger logger = LoggerFactory.getLogger(ManagerController.class);

    //查询注册的所有group信息
    @RequestMapping("/groups.htm")
    public @ResponseBody ResultInfo groups() {
        RegistryManager rm = this.appContext.getBean(RegistryManager.class);
        List<String> groups = rm.getGroups();
        if (logger.isInfoEnabled()) {
            logger.info("groups [" + groups + "]");
        }
        DataList dataList = DataList.Builder.create().addValues("group", groups);
        return dataList;
    }

    @RequestMapping("/address.htm")
    public @ResponseBody ResultInfo address(@RequestParam String group, @RequestParam String type) {
        RegistryManager rm = this.appContext.getBean(RegistryManager.class);
        List<String> address = rm.getAddress(group, type);
        if (logger.isInfoEnabled()) {
            logger.info("group [" + group + "] type [" + type + "] address [" + address + "]");
        }
        DataList dataList = DataList.Builder.create().addValues("address", address);
        return dataList;
    }

    @RequestMapping("/services.htm")
    public @ResponseBody ResultInfo services(@RequestParam String group, @RequestParam String type, @RequestParam String address) {
        RegistryManager rm = this.appContext.getBean(RegistryManager.class);
        DataMap services = rm.getServices(group, type, address);
        if (logger.isInfoEnabled()) {
            logger.info("group [" + group + "] type [" + type + "] address [" + address + "] services [" + services + "]");
        }
        return services;
    }

    @RequestMapping("/serviceInfo.htm")
    public @ResponseBody ResultInfo serviceInfo(@RequestParam String service) {
        return execute(service, DataMap.Builder.create(), getRequestHolder());
    }
}
