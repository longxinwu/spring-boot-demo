package com.longxinwu;

import com.longxinwu.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring-boot单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SpringBootDemoApplicationTests {
    @Autowired
    Person person;
    @Test
    void contextLoads() {
        System.out.println(person);
        try{
            Runtime.getRuntime().exec("https://www.baidu.com");
        }catch (Exception e){
            log.info("open UI failed{}", e.getMessage());
        }

    }

    @Autowired
    ApplicationContext ioc;  //声明ioc容器

    @Test
    public void testHelloService(){
        boolean flag = ioc.containsBean("helloService");
        System.out.println(flag);
    }

}
