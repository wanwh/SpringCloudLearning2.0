package com.wanwh.core.fliter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器
 * Created by wanwh on 2019/7/18 0018.
 */
public class SpringCloudLearningFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(SpringCloudLearningFilter.class);

    /**
     * 过滤器类型
     * @return "pre" 请求之前执行
     */
    @Override
    public String filterType() {
        return null;
    }

    /**
     * 过滤器执行顺序，项目中同时存在多个过滤器时的order顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断过滤器是否生效，  开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器业务代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext rtx = RequestContext.getCurrentContext();
        HttpServletRequest request = rtx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        //从请求头中获取token
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            rtx.setSendZuulResponse(false);
            rtx.setResponseStatusCode(401);
            try {
                rtx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}
