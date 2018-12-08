package com.shoppingmall.serviceribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FirstService {
    //通过该方法使用负载均衡
    @Autowired
    RestTemplate restTemplate;

    /**
     * 通过之前注入ioc容器的restTemplate来消费eureka-client服务的“/hi”接口，在这里我们直接用的程序名替代了具体的url地址，
     * 在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名，
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "firstError")  //使用在该方法上使用熔断，该方法报错后，返回fallbackMehtod=“xxx”方法
    public String firstService(String name){
        //getForObject(url，返回的类型.class)
        return restTemplate.getForObject("http://service-client/hi?name="+name,String.class);
    }

    /**
     * 当service-ribbon服务不可用时，开启熔断，返回该方法内容。可以直接使用传入的参数。
     *  service-ribbon 工程不可用的时候，service-ribbon调用 service-hi的API接口时，会执行快速失败，
     *  直接返回一组字符串，而不是等待响应超时，这很好的控制了容器的线程阻塞。
     * @param name2
     * @return
     */
    public  String firstError(String name2){
        return "hi "+name2+", sorry error";
    }
}
