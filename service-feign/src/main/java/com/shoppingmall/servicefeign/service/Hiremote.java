package com.shoppingmall.servicefeign.service;

import com.shoppingmall.servicefeign.fallback.FeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * //@FeignClient(name = "service-client") 通过@FeignClien注解调用其他服务
 */
@FeignClient(name = "service-client",fallback = FeignFallback.class)   //@FeignClient(name = "调用的其他服务名",fallback="开启熔断后，服务失效返回的错误提示")
public interface Hiremote {
    /**
     * Feign会自动实例化带有@FeignClient注解接口中的方法
     * 此方法的地址和参数需要和远程调用的方法相同
     * @PostMapping(value = "接口地址") 使用post请求方法调用 还其他请求方法
     * @param name
     * @return
     */
    //使用@RequestMapping注解调用service-client服务中的接口
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String hiConsumer(@RequestParam(value = "name")String name);
}
