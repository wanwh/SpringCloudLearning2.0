package com.wanwh.error;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.wanwh.api.core.HpeisExceptionHandler;
import com.wanwh.api.core.result.GlobalResult;
import com.wanwh.api.core.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanwh on 2019/7/18 0018.
 */
@RestControllerAdvice
public class GetwayExceptionHandler extends HpeisExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GetwayExceptionHandler.class);

    @Override
    @SuppressWarnings("Duplicates")
    @ExceptionHandler(Exception.class)
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        if(isAjax(request)){
            GlobalResult result = new GlobalResult();
            if (ex instanceof NoHandlerFoundException) {
                result.setCode(ResultCode.NOT_FOUND.getCode());
                result.setMessage(ResultCode.NOT_FOUND.getMessage());
            } else if (ex instanceof ServletException) {
                result.setCode(ResultCode.FAIL.getCode());
                result.setMessage(ex.getMessage());
            }else {
                //系统内部异常,不返回给客户端,内部记录错误日志
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode());
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            ex.getMessage());
                } else {
                    message = ex.getMessage();
                }
                result.setMessage("操作失败");
                logger.error(message, ex);
            }
            result.setSuccess(false);
            return result;
        }else {
            ModelAndView mv = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap();
            if (ex instanceof NoHandlerFoundException) {
                attributes.put("code",ResultCode.NOT_FOUND.getCode());
                attributes.put("message",ResultCode.NOT_FOUND.getMessage());
            } else if (ex instanceof ServletException) {
                attributes.put("code",ResultCode.FAIL.getCode());
                attributes.put("message",ex.getMessage());
            }else {
                //系统内部异常,不返回给客户端,内部记录错误日志
                attributes.put("code",ResultCode.INTERNAL_SERVER_ERROR.getCode());
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            ex.getMessage());
                } else {
                    message = ex.getMessage();
                }
                logger.error(message, ex);
                attributes.put("message","操作失败");
            }
            attributes.put("success",false);
            view.setAttributesMap(attributes);
            mv.setView(view);
            return mv;
        }
    }

    private boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
