package com.wanwh.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanwh on 2018/7/11 0011.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory
            .getLogger(JsonUtil.class);

    public static final <T> List<T> getList(String jsontext, String list_str,
                                            Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(list_str);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }
    /**
     * @param <T>
     *            -> DepartmentBean
     * @param jsontext
     *            -> jsontext
     * @param obj_str
     *            -> department
     * @param clazz
     *            -> DepartmentBean
     * @return -> T
     */
    public static final <T> T getObject(String jsontext, String obj_str,
                                        Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(obj_str, clazz);
        } else {
            logger.info(obj.getClass()+"");
        }

        return null;
    }

    /**
     * @param <T>
     * @param jsontext
     *            ->
     * @param clazz
     *            -> UserBean.class
     * @return -> UserBean
     */
    // 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsontext, e);
        }
        return t;
    }

    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * @Description： json字符串转成为List
     * @author: wanwh
     * @date: 2018-7-11 17:24:36
     * @param jsonStr
     *            json字符串
     * @param clazz
     *            class名称
     * @return
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     *
     * @Description： json字符串转换成list<Map>
     * @author: wanwh
     * @date: 2018-7-11 17:24:24
     * @param jsonString
     *            json字符串
     * @return
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            logger.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * @Description： json字符串转换为Map
     * @author: wanwh
     * @date: 2018-7-11 17:24:11
     * @param jsonStr
     *            json字符串
     * @return
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }

    /**
     * wanwh return modeal
     * @param map
     * @return
     */
    public static final Map<String,Object> returnMap(Map map){

        Map<String,Object> modelMap = new HashMap<String,Object>(3);
        modelMap.put("obj", map);
        modelMap.put("success", true);
        modelMap.put("message", "操作成功！");

        return modelMap;
    }

}
