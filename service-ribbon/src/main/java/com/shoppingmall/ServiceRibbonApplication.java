package com.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 第二章：服务消费者(rest+ribbon) 整合第四章的断路器Hystrix。
 * 在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于http restful的。
 * Spring cloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign。这里先使用ribbon+restTemplate。
 * ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon。
 *
 * 操作步骤：
 * 一个服务注册中心，eureka server,端口为8761
 * eureka-client工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
 * sercvice-ribbon端口为8764,向服务注册中心注册
 * 当sercvice-ribbon通过restTemplate调用eureka-client的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用eureka-client：8762和8763 两个端口的hi接口；
 */
@SpringBootApplication
@EnableEurekaClient //表明这是提供者，向注册中心注册
@EnableDiscoveryClient  //表明该服务有向注册中心注册和向注册中心调用其他服务的功能。
@EnableHystrix  //开启熔断机制
public class ServiceRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRibbonApplication.class, args);
    }

    @Bean //注入bean
    @LoadBalanced //该注解表明这个方法开启负载均衡模式
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
