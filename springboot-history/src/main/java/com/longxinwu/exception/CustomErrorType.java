package com.longxinwu.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author wang bo
 * @Date 2021/3/18 下午10:38
 */
@Data
public class CustomErrorType {
    private Integer statusValue;
    private String message;

    public CustomErrorType(int statusValue, String msg){
        this.statusValue = statusValue;
        this.message = msg;
    }
}
