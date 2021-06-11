package longxinwu;


import lombok.extern.slf4j.Slf4j;
import longxinwu.bean.Person;
import longxinwu.controller.UploadFileController;
import longxinwu.service.FileService;
import longxinwu.singleton.Singleton;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResizableByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.UUID;

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
        //String path = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/1.jpeg";
        String path = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/2gifdpi.png";
        File file = new File(path);
        ImageInfo imageInfo = Imaging.getImageInfo(file);
        long width = imageInfo.getWidth();
        long height = imageInfo.getHeight();
        double physicalWidth = imageInfo.getPhysicalWidthInch() * 25.4;
        double physicalHeight = imageInfo.getPhysicalHeightInch() * 25.4;
        int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
        int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();
        BufferedImage img = ImageIO.read(file);

        BufferedImage scaleImg = null;
        double rate = width / height;
        int scaleWidth = 0;
        int scaleHeight = 0;
        if(rate > 3.125){
            double rate1 = width / 100;
            double height1 = height * rate1;
            scaleWidth = (int) width;
            scaleHeight = (int) height1;
            //scaleImg = scaleImage(img, scaleWidth, scaleHeight);

        }
        if(rate < 3.125){
            double rate2 = height / 32;
            double width1 = width * rate2;
            int scaleWidthInt = (int) width1;
            int heightInt = (int) height;
            //scaleImg = scaleImage(img, scaleWidthInt, heightInt);
        }
        //ImageIO.write(scaleImg, "png", new File("/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/33.png"));

    }

    public void drawPicture(int width, int height){
        int alphaType = BufferedImage.TYPE_INT_RGB;
        BufferedImage back = new BufferedImage(width, height, alphaType);
        Graphics2D g = back.createGraphics();
        //g.drawImage(backGroundImage, 0, 0, null);
    }


    @Autowired
    FileService fileService;
    @Test
    public void reSizePic() throws  Exception{
        String path = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/1gif.gif";
        String path2 = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/4gif.jpg";
        String path3 = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/4gif.jpg";
        File inFile = new File(path);
        File outFile = new File(path2);
        File outFileDpi = new File(path3);
        BufferedImage oriImg = ImageIO.read(inFile);
        BufferedImage reSetTypeImg = fileService.changeImgType(oriImg, oriImg.getWidth(), oriImg.getHeight());
        ImageIO.write(reSetTypeImg, "jpg", outFile);
        fileService.setImgDpi(outFile, outFileDpi);
    }

    @Test
    public void tiffToJpg(){
        String oldPath = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/2gifdpi.jpg";
        String newPath = "/Users/wangbo-mac/Documents/demo/spring-boot-demo/src/main/resources/pic/2gifdpi.png";
        try {
            BufferedImage bufferedImage=ImageIO.read(new File(oldPath));
            ImageIO.write(bufferedImage,"jpeg",new File(newPath));//可以是png等其它图片格式
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandom(){
        String str = UUID.randomUUID().toString().replaceAll("-","");
        String str1 = "1234567890up";
        String str2 = "up";
        int index = str1.indexOf(str2);
        String str3 = str1.substring(0, index);
        System.out.println(str3);
    }

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void testRedis(){
        String key = "captcha";
        redisTemplate.opsForValue().set(key, 1);
        Long count = redisTemplate.opsForValue().increment(key,1L);
        System.out.println(redisTemplate.getExpire(key));
    }
}
