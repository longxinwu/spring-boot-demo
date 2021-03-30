package com.longxinwu.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author wang bo
 * @Date 2021/1/23 下午7:36
 */
@Data
public class User {
    private String userName;

    private String age;

    private boolean acq;

    private boolean alter;

    private MultipartFile[] logos;
}
