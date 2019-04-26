package com.shoppingmall.configserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;


/**
 * Spring Cloud Config也提供本地存储配置的方式。我们只需要在配置文件中设置属性spring.profiles.active=native，
 * Config Server会默认从应用的src/main/resource目录下检索配置文件。
 * 也可以通过spring.cloud.config.server.native.searchLocations=file:E:/properties/属性来指定配置文件的位置。
 * 虽然Spring Cloud Config提供了这样的功能，但是为了支持更好的管理内容和版本控制的功能，还是推荐使用git的方式。
 *
 * 关于读取的配置文件数组是空的情况：
 * Spring Cloud Config分服务端和客户端，服务端负责将git（svn）中存储的配置文件发布成REST接口，客户端可以从服务端REST接口获取配置。
 * 仓库中的配置文件会被转换成web接口，访问可以参照以下的规则：
 *     /{application}/{profile}[/{label}]
 *     /{application}-{profile}.yml
 *     /{label}/{application}-{profile}.yml
 *     /{application}-{profile}.properties
 *     /{label}/{application}-{profile}.properties
 *
 * 以git库的config-dev.properties为例子，它的application是config，profile是dev。其访问格式就是：localhost:端口/config/dev。如果分支是
 * master，可以省略分支路径。
 * client会根据填写的参数来选择读取对应的配置。
 */
@SpringBootApplication
@EnableConfigServer //表明这是配置中心的服务端，读取远程配置文件，转换为rest接口服务
@EnableDiscoveryClient //服务发现，向注册中心注册该服务
public class ConfigServerApplication {
    //读取配置文件中git仓库地址
    @Value("${spring.cloud.config.server.git.uri}")
    private String gitUrl;

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @Bean
    public String getUrl(){
        System.out.println(this.gitUrl);
        return gitUrl;
    }

}
