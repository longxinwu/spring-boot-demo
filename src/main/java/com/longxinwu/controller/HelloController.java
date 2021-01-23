package com.longxinwu.controller;

import com.longxinwu.bean.Person;
import com.longxinwu.service.GetIpAndPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bo wang
 * @version 1.0
 * @date 2020/7/26 23:07
 */
@RestController
public class HelloController {
    @Autowired
    GetIpAndPortService getIpAndPortService;
    @Autowired
    Person person;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello port %s!", getIpAndPortService.getServerPort());
    }

    @GetMapping("/getUrl")
    public String getUrl(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello port %s!", getIpAndPortService.getUrl());
    }

    @GetMapping("getPerson")
    public Person person(){
        return person;
    }
}
