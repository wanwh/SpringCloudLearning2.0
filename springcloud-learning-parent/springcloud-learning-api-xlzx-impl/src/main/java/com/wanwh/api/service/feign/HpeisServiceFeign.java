package com.wanwh.api.service.feign;

import com.wanwh.api.service.IHpeisService;
import com.wanwh.api.service.fallback.HpeisServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author wanwh
 * @create 2019/7/15 0015 22:02
 */
@FeignClient(value = "app-wanwh-hpeis",fallback = HpeisServiceFallback.class)
public interface HpeisServiceFeign extends IHpeisService {

}
