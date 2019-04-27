package com.shoppingmall.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 第六章-2：springcloud config 配置中心-client端
 *
 * springcloud config 统一配置的流程：
 * springcloud config的客户端通过配置文件的uri找到config服务端，然后服务端会去远程仓库获取配置文件，最后以接口的形式返回到config的客户端。
 * Spring Cloud Config分服务端和客户端，服务端负责将git（svn）中存储的配置文件发布成REST接口，客户端可以从服务端REST接口获取配置。
 *  但客户端并不能主动感知到配置的变化，从而主动去获取新的配置。
 *  <br>
 *  解决客户端无法主动获取配置变化的问题：
 *   (1)客户端如何去主动获取新的配置信息呢，springcloud已经给我们提供了解决方案，每个客户端通过POST方法触发各自的/refresh。但是
 *      需要每次执行/refresh接口，win上面打开cmd执行curl -X POST http://localhost:8002/refres，很麻烦，尤其是有多个服务的
 *      情况下，需要将所有服务都调用一次/refues接口，还有第二种办法。
 *   (2)使用github自带的webHook功能：WebHook是当某个事件发生时，通过发送http post请求的方式来通知信息接收方。
 *      Webhook来监测你在Github.com上的各种事件，最常见的莫过于push事件。如果你设置了一个监测push事件的Webhook，那么每当你的这个项目有了任何提交，
 *      这个Webhook都会被触发，这时Github就会发送一个HTTP POST请求到你配置好的地址。如此一来，你就可以通过这种方式去自动完成一些重复
 *      性工作，比如，你可以用Webhook来自动触发一些持续集成（CI）工具的运作，比如Travis CI；又或者是通过 Webhook 去部署你的线
 *      上服务器。但是，如果有多个服务、多个客户端需要在github上配置很多个。也很麻烦。
 *    (3)最终版：使用springcloud bus 该组件就是在服务之间传播消息，被称为消息总线。Spring Cloud Bus 将分布式的节点用轻量的消息代理连接起来。
 *       它可以用于广播配置文件的更改或者服务之间的通讯，也可以用于监控。本文要讲述的是用Spring Cloud Bus实现通知微服务架构的配置文件的更改。
 *       比如，有多个服务需要更新配置文件，我们只需要更新A服务，A服务将最新配置信息发送给 springcloud bus，
 *       由springcloud bus再通知其他服务。这样，只需要通知一个服务，通过消息总线，其他服务的配置文件也可以更新。
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

}
