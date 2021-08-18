package com.mybatis.demo.netty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
    @GetMapping(path= "/netty")
    public String chat(){
        return "chat";
    }
}
