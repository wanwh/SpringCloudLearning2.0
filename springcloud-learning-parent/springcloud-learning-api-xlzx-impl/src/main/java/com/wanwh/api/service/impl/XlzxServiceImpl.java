package com.wanwh.api.service.impl;

import com.wanwh.api.service.IXlzxService;
import com.wanwh.api.service.feign.HpeisServiceFeign;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:37
 */
@RestController
@RefreshScope
public class XlzxServiceImpl implements IXlzxService {

    @Resource
    private HpeisServiceFeign hpeisServiceFeign;

    @RequestMapping("/xlzxToHpeis")
    public String xlzxToHpeis(@RequestParam("name") String name) {
        return "获取到信息："+ hpeisServiceFeign.getMember(name).toString();
    }
}
