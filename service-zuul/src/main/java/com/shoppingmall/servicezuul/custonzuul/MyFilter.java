package com.shoppingmall.servicezuul.custonzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现自定义Filter。
 *
 * Zuul大部分功能都是通过过滤器来实现的，这些过滤器类型对应于请求的典型生命周期。
 *   PRE： 这种过滤器在请求被zuul路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 *   ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 *   POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 *   ERROR：在其他阶段发生错误时执行该过滤器。 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，
 *          我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
 */
@Component //表示这是个组件，注入到spring容器中
public class MyFilter extends ZuulFilter {

    /**
     * 定义filter的类型，有pre、routing、post、error 四种类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 定义filter的执行顺序：
     * 数字越小，执行顺序越高，越先执行。
     *
     * 类型  顺序           过滤器                       功能
     * pre 	  -3 	     ServletDetectionFilter 	标记处理Servlet的类型，检测请求是用 DispatcherServlet还是 ZuulServlet
     * pre 	  -2 	     Servlet30WrapperFilter 	在Servlet 3.0 下，包装 requests
     * pre 	  -1 	     FormBodyWrapperFilter 	    解析表单数据
     * route   1 	     DebugFilter 	            设置请求过程是否开启debug
     * route   5 	     PreDecorationFilter 	    根据uri决定调用哪一个route过滤器
     * route   10 	     RibbonRoutingFilter 	    如果写配置的时候用ServiceId则用这个route过滤器，该过滤器可以用Ribbon 做负载均衡，用hystrix做熔断
     * route   100       SimpleHostRoutingFilter 	如果写配置的时候用url则用这个route过滤
     * route   500       SendForwardFilter 	        用RequestDispatcher请求转发，forward请求转发
     * error     0 	     SendErrorFilter 	        处理有错误的请求响应
     * post    1000      SendResponseFilter 	    用RequestDispatcher请求转发，处理正常的请求响应
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return -3;
    }

    /**
     * 表示是否需要执行该filter，true表示执行，false表示不执行
     * 若是true，则执行下面的run方法，否则不执行。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * filter需要执行的具体逻辑操作
     * @return
     */
    @Override
    public Object run() {
        //RequestContext该类是zuul存储了整个request请求，并被所有的zuulFilter共享
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        //前台请求向后台传一个token参数，后台接收，如果是token是空 就不路由。
        if(token!=null&&!token.equals("")){
            //ctx.setSendZuulResponse(boolean值) 是否对请求进行路由，true 是，false 否
            ctx.setSendZuulResponse(true);
            //路由的状态码
            ctx.setResponseStatusCode(200);
            // 设值，让下一个Filter看到上一个Filter的状态
            ctx.set("isSuccess",true);
        }else{
            //不对其进行路由
            ctx.setSendZuulResponse(false);
            //返回状态码
            ctx.setResponseStatusCode(400);
            //返回错误信息，可以是json格式或其他格式
            ctx.setResponseBody("token is enpty");
            // // 设值，让下一个Filter看到上一个Filter的状态
            ctx.set("isSuccess",false);
        }
        return null;
    }
}
