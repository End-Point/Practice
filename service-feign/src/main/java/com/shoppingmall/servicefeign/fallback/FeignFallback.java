package com.shoppingmall.servicefeign.fallback;

import com.shoppingmall.servicefeign.service.Hiremote;
import org.springframework.stereotype.Component;

/**
 * 服务出错，开启熔断后调用的方法，实现使用@FeignClient注解调用其他服务的接口
 */
@Component
public class FeignFallback implements Hiremote {
    @Override
    public String hiConsumer(String name) {
        return "sorry "+name;
    }
}
