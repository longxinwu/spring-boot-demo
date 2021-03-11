package com.longxinwu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wang bo
 * @Date 2021/3/11 下午2:18
 */
@Controller
@Slf4j
public class UploadFileController {

    @GetMapping(path = "/initUpload")
    public String initUpload(HttpServletRequest request){
        request.setAttribute("title", "upload logo");
        return "uploadFile";
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam(value = "logos") MultipartFile[] logos, HttpServletRequest request){

        for(int i=0; i<logos.length; i++){
            MultipartFile newFile = logos[i];
            log.info("origin file name:{}", newFile.getOriginalFilename());
        }
        return "success";
    }
}
