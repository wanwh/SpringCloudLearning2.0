package com.wanwh.springcloudlearningadmin.util.wx;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanwh on 2019/8/27 0027.
 */
public class Util {

    public static Map<String, WeChatTemplateData> getWeChatTemplate(String firstValue,String secondValue,String thirdValue,String fourthValue,String remark) {
        Map<String, WeChatTemplateData> m = new HashMap<String, WeChatTemplateData>(5);

        WeChatTemplateData first = new WeChatTemplateData();
        first.setColor("#458B00");
        first.setValue(firstValue);
        m.put("first", first);

        WeChatTemplateData keyword1 = new WeChatTemplateData();
        keyword1.setColor("#000000");
        keyword1.setValue(secondValue);
        m.put("keyword1", keyword1);

        WeChatTemplateData keyword2 = new WeChatTemplateData();
        keyword2.setColor("#000000");
        keyword2.setValue(thirdValue);
        m.put("keyword2", keyword2);

        WeChatTemplateData keyword3 = new WeChatTemplateData();
        keyword3.setColor("#000000");
        keyword3.setValue(fourthValue);
        m.put("keyword3", keyword3);

        WeChatTemplateData remarkData = new WeChatTemplateData();
        remarkData.setColor("#000000");
        remarkData.setValue(remark);
        m.put("remark", remarkData);

        return m;
    }
}
