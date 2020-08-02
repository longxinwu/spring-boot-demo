package com.longxinwu.service;

import com.longxinwu.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wang bo
 * @Date 2020/8/2 4:36 下午
 */
@Slf4j
@Component
@ServerEndpoint("/warnLogSocket")
public class WebSocketWarnLogSocket {
    /**
     * 连接集合
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
    private static Map<String, Integer> lengthMap = new ConcurrentHashMap<String, Integer>();
    private final String WARN_LOG_SOCKET = "warnLogSocket";

    /**
     * * 开启新的webSocket请求
     */
    @OnOpen
    public void onOpen(Session session){
        //添加到集合中
        sessionMap.put(session.getId(), session);
        lengthMap.put(session.getId(), 1);//默认从第一行开始

        LogThread logThread = new LogThread(session, sessionMap, lengthMap, WARN_LOG_SOCKET);
        logThread.start();
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
