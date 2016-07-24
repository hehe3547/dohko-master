package com.dohko.core.web;

import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.util.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HtmController extends BaseController {

    protected ApplicationContext appContext;

    @RequestMapping("/time.htm")
    public @ResponseBody
    ResultInfo time() {
        DataMap retMap = DataMap.Builder.create().put("time", DateUtils.getCurrentDate());
        return retMap;
    }
    @RequestMapping("/**")
    public @ResponseBody ResultInfo execute(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        return super.execute(httpReq, httpRes);
    }
}
