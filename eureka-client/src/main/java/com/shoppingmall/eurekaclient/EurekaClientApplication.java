package com.shoppingmall.eurekaclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第一章(2)：创建服务提供者
 * 当client向server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。
 * Eureka server 从每个client实例接收心跳消息。 如果心跳超时，则通常将该实例从注册server中删除。
 */
@SpringBootApplication
@EnableEurekaClient //表明这是提供者,并向服务中心注册
@RestController //相当于@Controller
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    //使用@Value注解 将yml文件中的属性值注入到该属性中
    @Value("${server.port}")
    String port;

    /**
     *访问方法 http://localhost:8762/hi?name=123
     * @param name
     * @return
     */
    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hello " + name + " ,i am from port:" + port;
    }
}
