package com.wanwh.api.service.impl;

import com.wanwh.api.core.result.GlobalResult;
import com.wanwh.api.core.result.GlobalResultGenerator;
import com.wanwh.api.entity.User;
import com.wanwh.api.service.IHpeisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:10
 */
@RestController
@RefreshScope
public class HpeisServiceImpl implements IHpeisService {

    @Value("${server.port}")
    private String service_port;
    //http://127.0.0.1:8882/actuator/refresh  手动刷新配置文件地址
    @Override
    @RequestMapping("/getMember")
    public GlobalResult getMember(@RequestParam("name") String name) {
        User user = new User();
        user.setName(service_port);
        user.setAge(20);
        return GlobalResultGenerator.genSuccessResult(user);
    }

}
