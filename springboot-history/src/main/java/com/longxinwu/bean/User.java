package com.longxinwu.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Author wang bo
 * @Date 2021/1/23 下午7:36
 */
@Data
public class User implements Serializable {
    private String userName;

    private String age;

    private boolean acq;

    private boolean alter;

    private MultipartFile[] logos;
}
