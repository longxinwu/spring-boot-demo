package com.mybatis.demo.netty;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 发送者
     */
    private String send;
    /**
     * 接收者
     */
    private String receive;

    private String id;

    private String info;
    /**
     * 消息类型 1 上线 2 发消息
     */
    private int type;
}
