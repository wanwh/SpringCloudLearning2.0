package com.wanwh.core.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wanwh
 * @create 2019/7/22 0022 21:33
 */
@Component
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider{

    // zuul配置能够使用config实现实时更新
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        // app-itmayiedu-order
        resources.add(swaggerResource("app-wanwh-hpeis", "/api-hpeis/v2/api-docs", "2.0"));
        resources.add(swaggerResource("app-wanwh-xlzx", "/api-xlzx/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
