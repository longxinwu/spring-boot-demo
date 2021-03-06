package com.longxinwu.controller;

import com.longxinwu.bean.User;
import com.longxinwu.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author wang bo
 * @Date 2021/3/11 下午2:18
 */
@Controller
@Slf4j
public class UploadFileController {

    @Autowired
    FileService fileService;

    @GetMapping(path = "/initUpload")
    public String initUpload(HttpServletRequest request){
        request.setAttribute("title", "upload logo");
        return "uploadFile";
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam(value = "logos") MultipartFile[] logos, HttpServletRequest request){
        for(int i=0; i<logos.length; i++){
            MultipartFile newFile = logos[i];
            long fileSize = newFile.getSize();
            log.info("origin file name:{}", newFile.getOriginalFilename());
        }
        return "success";
    }

    @PostMapping(path = "/upload2")
    public String uploadFile2(User user, HttpServletRequest request) throws Exception{
        MultipartFile[] files = user.getLogos();
        if(user.isAcq()){
            log.info("前段传boolean值参数");
        }

        MultipartFile file = null;
        for(int i=0; i<files.length; i++){
            file = files[i];
            //getImgInfo(file);
            long fileSize = file.getSize();
            boolean Empty = file.isEmpty();
            log.info("origin file name:{}", file.getOriginalFilename());
            File newFile = fileService.multipartFiletoFile(file);
        }
        return "success";
    }

    public void getImgInfo(File logo) throws Exception{
        ImageInfo imageInfo = Imaging.getImageInfo(logo);
        long width = imageInfo.getWidth();
        long height = imageInfo.getHeight();
        double physicalWidth = imageInfo.getPhysicalWidthInch() * 25.4;
        double physicalHeight = imageInfo.getPhysicalHeightInch() * 25.4;
        int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
        int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();
        log.info("pixel width:{}",String.valueOf(width));
        log.info("pixel height:{}",String.valueOf(height));
        log.info("physicalWidth:{}",String.valueOf(physicalWidth));
        log.info("physicalHeight:{}",String.valueOf(physicalHeight));
        log.info("physicalWidthDpi:{}",String.valueOf(imageInfo.getPhysicalWidthDpi()));
        log.info("physicalHeightDpi:{}",String.valueOf(physicalHeightDpi));
    }

    private BufferedImage scaleImage(BufferedImage srcImage, int width, int height) {
        Image image = srcImage.getScaledInstance(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

}
