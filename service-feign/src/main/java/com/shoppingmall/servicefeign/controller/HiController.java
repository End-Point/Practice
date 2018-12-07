package com.shoppingmall.servicefeign.controller;

import com.shoppingmall.servicefeign.service.Hiremote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "hiController" )
public class HiController {

    //注入接口
    @Autowired
    private Hiremote hiremote;

    @RequestMapping(value = "/hiConsumer")
    @ResponseBody
    public String hiConsumer(@RequestParam(defaultValue = "helloWorld") String name){
       return  hiremote.hiConsumer(name);
    }
}
