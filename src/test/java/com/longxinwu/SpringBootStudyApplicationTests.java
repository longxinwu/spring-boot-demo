package com.longxinwu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class SpringBootStudyApplicationTests {

    @Test
    void contextLoads() {
        File file = new File("./log/simulator.log");

        String filePath = System.getProperty("user.home");
        String filePath2 = System.getProperty("user.home") + "/log/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/simulator.log";
        System.out.println(file.exists()+ filePath +"--"+ filePath2);
    }

}
