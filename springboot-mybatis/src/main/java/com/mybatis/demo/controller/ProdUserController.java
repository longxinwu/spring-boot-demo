package com.mybatis.demo.controller;

import com.mybatis.demo.Service.ProdUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
@Slf4j
@Api(tags = "/prodUser")
public class ProdUserController {

    @Autowired
    ProdUserService prodUserService;
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value = "/addUser")
    @GetMapping("/addUser")
    public String addProdUser(){
        redisTemplate.opsForValue().set("user", "user1");
        //return prodUserService.getUserInfo(id).toString();
        return "add user to redis succcess";
    }
    @ApiOperation(value = "/getUserFormRedis")
    @GetMapping("/getUserFormRedis")
    public String getFromRedis(){
        return (String) redisTemplate.opsForValue().get("user");
    }
    @ApiOperation(value = "获取用户",notes = "根据id获取")
    @GetMapping("/getUser")
    public String getProdUser(int id){
        log.info("info级别的日志");
        log.warn("warn级别的日志");
        log.error("error级别的日志");
        return prodUserService.getUserInfo(id).toString();
    }
    @ApiOperation(value = "获取用户密码列表")
    @PostMapping("/getList")
    public List<String> pwdList(){
        List<String> list = prodUserService.getPwdList();
        return list;
    }

}
