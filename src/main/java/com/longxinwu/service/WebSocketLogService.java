package com.longxinwu.service;/**
 * @Author wangbo6
 * @Date 2020/7/30 11:11
 */
import com.longxinwu.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wangbo6
 * @Date 2020/7/30 11:11
 */
@Slf4j
@ServerEndpoint("/logSocket")
@Component
public class WebSocketLogService {
    /**
     * 连接集合
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
    private static Map<String, Integer> lengthMap = new ConcurrentHashMap<String, Integer>();
    private final String LOG_SOCKET = "logSocket";
    /**
     * * 开启新的webSocket请求
     */
    @OnOpen
    public void onOpen(Session session){

        try {
            send(session,"连接成功");
        } catch (Exception e) {
            log.info("websocket IO异常");
        }
        //添加到集合中
        sessionMap.put(session.getId(), session);
        lengthMap.put(session.getId(), 1);//默认从第一行开始

        LogThread logThread = new LogThread(session, sessionMap, lengthMap, LOG_SOCKET);
        logThread.start();

        //获取日志信息
        /*new Thread(() -> {
            log.info("LoggingWebSocketServer 任务开始");
            boolean first = true;
            while (sessionMap.get(session.getId()) != null) {
                BufferedReader reader = null;
                try {
                    //日志文件路径，获取最新的
                    String filePath = "./logs/simulator.log";
                    //字符流
                    reader = new BufferedReader(new FileReader(filePath));
                    Object[] lines = reader.lines().toArray();

                    //只取从上次之后产生的日志
                    Object[] copyOfRange = Arrays.copyOfRange(lines, lengthMap.get(session.getId()), lines.length);

                    //对日志进行着色，更加美观  PS：注意，这里要根据日志生成规则来操作
                    for (int i = 0; i < copyOfRange.length; i++) {
                        String line = (String) copyOfRange[i];
                        //先转义
                        line = line.replaceAll("&", "&amp;")
                                .replaceAll("<", "&lt;")
                                .replaceAll(">", "&gt;")
                                .replaceAll("\"", "&quot;");

                        //处理等级
                        line = line.replace("DEBUG", "<span style='color: blue;'>DEBUG</span>");
                        line = line.replace("INFO", "<span style='color: green;'>INFO</span>");
                        line = line.replace("WARN", "<span style='color: orange;'>WARN</span>");
                        line = line.replace("ERROR", "<span style='color: red;'>ERROR</span>");

                        //处理类名
                        String[] split = line.split("]");
                        if (split.length >= 2) {
                            String[] split1 = split[1].split("-");
                            if (split1.length >= 2) {
                                line = split[0] + "]" + "<span style='color: #298a8a;'>" + split1[0] + "</span>" + "-" + split1[1];
                            }
                        }

                        copyOfRange[i] = line;
                    }

                    //存储最新一行开始
                    lengthMap.put(session.getId(), lines.length);

                    //第一次如果太大，截取最新的200行就够了，避免传输的数据太大
                    if(first && copyOfRange.length > 200){
                        copyOfRange = Arrays.copyOfRange(copyOfRange, copyOfRange.length - 200, copyOfRange.length);
                        first = false;
                    }

                    String result = StringUtils.join(copyOfRange,"<br/>");
                    //String result = StringUtils.join(copyOfRange,"<br/>");
                    //发送
                    send(session, result);

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
        }).start();*/

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //从集合中删除
        sessionMap.remove(session.getId());
        lengthMap.remove(session.getId());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {

    }

    /**
     * 封装一个send方法，发送消息到前端
     */
    private void send(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
            //String path = "./log/copy.txt";
            //FileUtil.saveData(path,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
