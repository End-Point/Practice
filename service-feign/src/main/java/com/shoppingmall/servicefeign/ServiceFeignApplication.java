package com.shoppingmall.servicefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

/**
 * 第三章：服务消费(调用)者(fegin)：整合第四章的断路器Hystrix。
 * Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，
 * 可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
 * 简而言之：
 *     Feign 采用的是基于接口的注解
 *     Feign 整合了ribbon，具有负载均衡的能力
 *     整合了Hystrix，具有熔断的能力
 *  准备工作:继续用上一章的工程， 启动eureka-server，端口为8761; 启动service-hi 两次，端口分别为8762 、8773.
 */
@SpringBootApplication
@EnableFeignClients  //开启Feign负载均衡功能
@EnableEurekaClient //表明这是客户端 需要向注册中心注册
@EnableDiscoveryClient //表明该服务有向注册中心注册和向注册中心调用其他服务的功能
public class ServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }
}
