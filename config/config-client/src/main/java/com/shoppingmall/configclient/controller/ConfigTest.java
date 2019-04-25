package com.shoppingmall.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RefreshScope //动态刷新配置文件
@RequestMapping(value = "configTest")
public class ConfigTest {

    /*读取配置信息的格式为 @Value("${配置文件的key值}")  */
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping(value = "getProfile")
    @ResponseBody
    public String getProfile(){
        System.out.println("git远程仓库的配置信息为："+hello);
       return this.hello;
    }
}
