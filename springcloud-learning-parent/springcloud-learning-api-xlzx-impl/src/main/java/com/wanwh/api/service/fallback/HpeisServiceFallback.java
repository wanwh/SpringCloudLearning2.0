package com.wanwh.api.service.fallback;

import com.wanwh.api.core.result.GlobalResult;
import com.wanwh.api.core.result.GlobalResultGenerator;
import com.wanwh.api.service.feign.HpeisServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @Author wanwh
 * @create 2019/7/16 0016 22:49
 */
@Component
public class HpeisServiceFallback implements HpeisServiceFeign {

    @Override
    public GlobalResult getMember(String name) {
        return GlobalResultGenerator.genErrorResult("服务器忙，请稍后重试！");
    }


}
