package com.longxinwu.config;

import com.longxinwu.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    /**
     * 将方法的返回值，添加到容器中,容器中这个组件默认的id就是方法名
     * @return
     */
    @Bean
    public HelloService helloService(){
        return new HelloService();
    }
}
