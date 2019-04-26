package com.shoppingmall.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 */
@Controller
@RefreshScope //首先在pom文件中加入jar包，然后使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
@RequestMapping(value = "configTest")
public class ConfigTest {

    /*读取配置信息的格式为 @Value("${远程配置文件的key值}") */
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping(value = "getProfile")
    @ResponseBody
    public String getProfile(){
        System.out.println("git远程仓库的配置信息为："+hello);
       return this.hello;
    }
}
