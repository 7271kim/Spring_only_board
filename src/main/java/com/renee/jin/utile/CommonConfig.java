package com.renee.jin.utile;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonConfig extends CommonConfigBean {
    public CommonConfig() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("../config/config.xml");
        CommonConfigBean cfg = (CommonConfigBean) ctx.getBean("config");
        
        /*
         * config.xml
         * com.seoularchi.model.CommonConfigBean.java
         * 위 파일과 아래 추가
         * */
        this.setDevelopment(cfg.getDevelopment());
        this.setAllowFile(cfg.getAllowFile());
        this.setAllowImage(cfg.getAllowImage());
        this.setMemUrl(cfg.getMemUrl());
        this.setMainUrl(cfg.getMainUrl());
        this.setSimsimUrl(cfg.getSimsimUrl());
        this.setAllowFileSize(cfg.getAllowFileSize());
        this.setLocalSimSimSaveFolder(cfg.getLocalSimSimSaveFolder());
        this.setRemoteSimSimSaveFolder(cfg.getRemoteSimSimSaveFolder());
    }
}

