package com.mybatis.demo.controller;

import com.mybatis.demo.Service.ProdUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Slf4j
public class ProdUserController {

    @Autowired
    ProdUserService prodUserService;
    @GetMapping("/getUser")
    public String getProdUser(int id){
        log.info("info级别的日志");
        log.warn("warn级别的日志");
        log.error("error级别的日志");
        return prodUserService.getUserInfo(id).toString();
    }
}
