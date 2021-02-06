package com.longxinwu.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WebsocketController {
    
    //跳转到websocket界面
    @RequestMapping(value="/webSocket")
    public String webSocket(Model model) {
        log.info("websocket 日志");
        return "websocket";
    }

    //跳转到logsocket界面
    @RequestMapping(value="/logSocket")
    public String logSocket(Model model) {
        log.info("log 日志");
        return "logsocket";
    }

    //跳转到warnLogSocket界面
    @RequestMapping(value="/warnLogSocket")
    public String warnLogSocket(Model model) {
        log.warn("你好 warn log");
        return "warnlogsocket";
    }
}