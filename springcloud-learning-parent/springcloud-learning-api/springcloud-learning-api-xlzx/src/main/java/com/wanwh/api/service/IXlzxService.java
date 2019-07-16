package com.wanwh.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:35
 */
public interface IXlzxService {

    //心理咨询调用体检系统
    @RequestMapping("/xlzxToHpeis")
    String xlzxToHpeis(@RequestParam("name")String name);
}
