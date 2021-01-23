package com.longxinwu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 该注解标注一个 主程序类，说明是一个spring-boot 应用
 */
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBootDemoApplication.class, args);
        String[] names = run.getBeanDefinitionNames();
        for(String str: names){
            System.out.println(str);
        }
    }
}
