package com.longxinwu.config;

import com.longxinwu.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @Configuration: 指明当前类是一个配置类，就是来代替之前的Spring配置文件的
 *  在原来的配置文件中用<bean></bean>标签添加组件
 *  而在配置类中用@Bean添加组件
 */
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
