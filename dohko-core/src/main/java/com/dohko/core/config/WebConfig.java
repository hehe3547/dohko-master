package com.dohko.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiangbin on 2016/6/25.
 */
@Configuration
@ConfigurationProperties(prefix = "web")
public class WebConfig {

    //欢迎页面
    private String welcome;
    //验证码个数
    private int codeSize = 4;
    private int imgWidth = 50;
    private int imgHeight = 18;
    private int imgFill = 88;
    private boolean benchmark = false;
    private int threadMax = 10;
    private int threadPer = 10;

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public int getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgFill() {
        return imgFill;
    }

    public void setImgFill(int imgFill) {
        this.imgFill = imgFill;
    }

    public boolean isBenchmark() {
        return benchmark;
    }

    public void setBenchmark(boolean benchmark) {
        this.benchmark = benchmark;
    }

    public int getThreadMax() {
        return threadMax;
    }

    public void setThreadMax(int threadMax) {
        this.threadMax = threadMax;
    }

    public int getThreadPer() {
        return threadPer;
    }

    public void setThreadPer(int threadPer) {
        this.threadPer = threadPer;
    }
}
