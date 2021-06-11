package com.longxinwu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wang bo
 * @Date 2020/8/10 11:30 下午
 */
@Controller
public class MappingController {
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostMapping(path = "/getUrl")
    public String getControllerUrl(){
        List<HashMap<String, String>> urlList = new ArrayList<HashMap<String, String>>();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                hashMap.put("url", url);
            }
            // 类名
            hashMap.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            hashMap.put("method", method.getMethod().getName());
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String type = methodsCondition.toString();
            // 方法名
            if (type != null && type.startsWith("[") && type.endsWith("]")) {
                type = type.substring(1, type.length() - 1);
                hashMap.put("type", type);
            }
            urlList.add(hashMap);
        }
        return urlList.toString();
    }
}
