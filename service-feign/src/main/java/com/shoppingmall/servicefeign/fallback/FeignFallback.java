package com.shoppingmall.servicefeign.fallback;

import com.shoppingmall.servicefeign.service.Hiremote;
import org.springframework.stereotype.Component;

/**
 * 服务出错，开启熔断后调用的方法，实现使用@FeignClient注解调用其他服务的Hiremote接口
 *
 * 由于网络原因或者自身的原因，服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet容器的
 *     线程资源会被消耗完毕，导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。
 *
 *   较底层的服务如果出现故障，会导致连锁故障。当对特定的服务的调用的不可用达到一个阀值（Hystric 是5秒20次） 断路器将会被打开。
 */
@Component
public class FeignFallback implements Hiremote {
    @Override
    public String hiConsumer(String name) {
        return "service-feign：sorry "+name;
    }
}
