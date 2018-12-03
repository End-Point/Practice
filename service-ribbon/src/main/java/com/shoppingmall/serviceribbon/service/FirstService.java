package com.shoppingmall.serviceribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FirstService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 通过之前注入ioc容器的restTemplate来消费eureka-client服务的“/hi”接口，在这里我们直接用的程序名替代了具体的url地址，
     * 在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名，
     * @param name
     * @return
     */
    public String firstService(String name){

        //getForObject(url，返回的类型.class)
        return restTemplate.getForObject("http://service-client/hi?name="+name,String.class);
    }
}
