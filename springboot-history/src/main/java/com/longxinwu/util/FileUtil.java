package com.longxinwu.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;


/**
 * by wang bo
 */
@Component
@Slf4j
public class FileUtil {
    public static void saveData(String urlStr, String data){
        log.info("saveData:{}",urlStr+data);
        BufferedWriter writer = null;
        File file = new File(urlStr);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            log.info("saveData1:{}",data);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false),"UTF-8"));
            writer.write(data);
            writer.flush();
            log.info("saveData2:{}", "完成");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String getData(String urlStr){
        BufferedReader reader = null;
        String readData = null;

        File file = new File(urlStr);
        log.info("file===:{}",file.exists());
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempStr = null;
            while((tempStr = reader.readLine()) != null){
                readData += tempStr;
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader !=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return readData;
    }
}
