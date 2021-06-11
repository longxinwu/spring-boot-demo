package com.longxinwu.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wang bo
 * @Date 2020/8/2 4:34 下午
 */
@Component
@Slf4j
public class LogThread extends Thread{

    /**
     * 连接集合
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    private static Map<String, Integer> lengthMap = new ConcurrentHashMap<String, Integer>();

    private final String logPath = "./logs/simulator.log";

    private final String LOG_SOCKET = "logSocket";

    private final String WARN_LOG_SOCKET = "warnLogSocket";

    private Session session;

    private String source;

    LogThread(){}

    public LogThread(Session session, Map<String, Session> sessionMap, Map<String, Integer> lengthMap, String source){
        this.sessionMap = sessionMap;
        this.lengthMap = lengthMap;
        this.session = session;
        this.source = source;
    }
    @Override
    public void run() {
        super.run();
        try{
            log.info("LoggingWebSocketServer 任务开始");
            boolean first = true;
            while (sessionMap.get(session.getId()) != null) {
                BufferedReader reader = null;
                try {
                    //字符流
                    reader = new BufferedReader(new FileReader(logPath));
                    Object[] lines = reader.lines().toArray();

                    //只取从上次之后产生的日志
                    Object[] copyOfRange = Arrays.copyOfRange(lines, lengthMap.get(session.getId()), lines.length);
                    List<String> warnList = new ArrayList<>();

                    //第一次如果太大，截取最新的200行就够了，避免传输的数据太大
                    if(first && copyOfRange.length > 200){
                        copyOfRange = Arrays.copyOfRange(copyOfRange, copyOfRange.length - 200, copyOfRange.length);
                        first = false;
                    }

                    //对日志进行着色，更加美观  PS：注意，这里要根据日志生成规则来操作
                    for (int i = 0; i < copyOfRange.length; i++) {
                        String line = (String) copyOfRange[i];
                        //先转义
                        /*line = line.replaceAll("&", "&amp;")
                                .replaceAll("<", "&lt;")
                                .replaceAll(">", "&gt;")
                                .replaceAll("\"", "&quot;");*/

                        //处理等级
                        //line = line.replace("DEBUG", "<span style='color: blue;'>DEBUG</span>");
                        //line = line.replace("INFO", "<span style='color: green;'>INFO</span>");
                        //line = line.replace("WARN", "<span style='color: orange;'>WARN</span>");
                        //line = line.replace("ERROR", "<span style='color: red;'>ERROR</span>");

                        //处理类名
                        /*String[] split = line.split("]");
                        if (split.length >= 2) {
                            String[] split1 = split[1].split("-");
                            if (split1.length >= 2) {
                                line = split[0] + "]" + "<span style='color: #298a8a;'>" + split1[0] + "</span>" + "-" + split1[1];
                            }
                        }*/
                        if(line.contains("WARN")){
                            warnList.add(line);
                        }
                        //copyOfRange[i] = line;
                    }

                    //存储最新一行开始
                    lengthMap.put(session.getId(), lines.length);

                    if(LOG_SOCKET.equals(source)){
                        String result = StringUtils.join(copyOfRange,"<br/>");
                        //发送
                        session.getBasicRemote().sendText(result);
                    }
                    if(WARN_LOG_SOCKET.equals(source)){
                        for(String msg : warnList){
                            session.getBasicRemote().sendText(msg);
                        }
                    }
                    //休眠一秒
                    Thread.sleep(1000);
                } catch (Exception e) {
                    //捕获但不处理
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            log.info("LoggingWebSocketServer 任务结束");
        }catch (Exception e){
            log.error("logThread error:{}", e);
        }
    }
}
