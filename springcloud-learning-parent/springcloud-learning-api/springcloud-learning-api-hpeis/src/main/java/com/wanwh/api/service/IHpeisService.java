package com.wanwh.api.service;

import com.wanwh.api.core.result.GlobalResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:02
 */
public interface IHpeisService {

    //实体类 存放在接口项目中
    @RequestMapping("/getMember")
    GlobalResult getMember(@RequestParam("name") String name);
}
