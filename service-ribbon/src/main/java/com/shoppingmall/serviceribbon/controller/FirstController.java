package com.shoppingmall.serviceribbon.controller;

import com.shoppingmall.serviceribbon.service.FirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 在浏览器上多次访问http://localhost:8764/firstController/hi?name=saaa:，浏览器交替显示：
 *     hi forezp,i am from port:8762
 *     hi forezp,i am from port:8763
 *  这说明当我们通过调用restTemplate.getForObject(“http://SERVICE-HI/hi?name=”+name,String.class)方法时，已经做了负载均衡，访问了不同的端口的服务实例。。
 */
@Controller
@RequestMapping("firstController")
public class FirstController {

    @Autowired
    private FirstService firstService;

    @RequestMapping("/hi")
    @ResponseBody  //如果不加这个注解，默认返回的是地址，地址找不到，会报404，加上该注解返回的是json数据
    public String hi(String name){
        System.out.println("进入firstController");
        return firstService.firstService(name);
    }
}
