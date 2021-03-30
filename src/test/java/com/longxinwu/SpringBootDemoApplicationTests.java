package com.longxinwu;

import com.longxinwu.bean.Person;
import com.longxinwu.controller.UploadFileController;
import com.longxinwu.singleton.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResizableByteArrayOutputStream;

import java.io.*;
import java.lang.reflect.Constructor;
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
    @Autowired
    UploadFileController uploadFileController;
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

    @Test
    public void testSingleton() {
        System.out.println("----------序列化----------");
        Singleton originSingleton = Singleton.getInstance();

        ByteArrayOutputStream bos = null;
        try {
            bos = new ResizableByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(Singleton.getInstance());
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Singleton serializeSingleton = (Singleton) ois.readObject();
            System.out.println("序列化操作");
            System.out.println(originSingleton == serializeSingleton);//false

            System.out.println("----------反射----------");
            Constructor<Singleton> cons = Singleton.class.getDeclaredConstructor();
            cons.setAccessible(true);
            Singleton reflextSingleton = cons.newInstance();
            System.out.println("反射操作");
            System.out.println(reflextSingleton == originSingleton);//false;

            /**
             * 如果单例对象在内部类的模式创建，则可以重写clone方式，clone方法会破坏单例模式
             */
            /*System.out.println("--------克隆--------");
            Singleton cloneSingleton = (Singleton) originSingleton.clone();
            System.out.println(cloneSingleton == originSingleton);//false*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFile() throws Exception{
        String path = "/Users/wangbo-mac/Pictures/Photos\\ Library.photoslibrary/resources/derivatives/6/6ACD407F-AFC2-4957-8A01-340812239ABA_1_105_c.jpeg";
        File file = new File(path);
        uploadFileController.getImgInfo(file);
    }

}
