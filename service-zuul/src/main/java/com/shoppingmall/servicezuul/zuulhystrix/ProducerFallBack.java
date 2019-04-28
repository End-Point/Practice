package com.shoppingmall.servicezuul.zuulhystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * zuul熔断,
 * zuul只支持服务级别的熔断，不支持URL级别的熔断。
 */
@Component
public class ProducerFallBack implements FallbackProvider {

    private Logger logger = LoggerFactory.getLogger(FallbackProvider.class);
    /**
     * 指定需要处理的service
     * @return
     */
    @Override
    public String getRoute() {
        return "service-ribbon";
    }


    /**
     * 若服务没有错误，返回该接口的信息
     * @return
     */
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("The service is unavailable.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }


    /**
     *  若服务出现错误，直接返回该方法的内容
     * @param route
     * @param cause
     * @return
     */
    @Override
    @ResponseBody
    public ClientHttpResponse fallbackResponse(String route,Throwable cause) {
         if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            logger.info("Excption {}",reason);
        }
        return this.fallbackResponse();
    }
}
