package com.mybatis.demo.controller;

import com.mybatis.demo.Service.ProdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class ProdUserController {

    @Autowired
    ProdUserService prodUserService;
    @GetMapping("/getUser")
    public String getProdUser(int id){
        return prodUserService.getUserInfo(id).toString();
    }
}
