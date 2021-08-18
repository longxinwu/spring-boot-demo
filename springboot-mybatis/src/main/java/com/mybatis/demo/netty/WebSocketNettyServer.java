package com.mybatis.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * netty服务器
 * 实现了DisposableBean 在容器销毁前会调用destroy 进行线程组的关闭
 */
@Data
@Slf4j
@Component
public class WebSocketNettyServer implements DisposableBean {

    /**
     * 自定义入站规则
     */
    @Autowired
    private WebSocketNettyHandler webSocketNettyHandler;

    /**
     * 自定义通道初始化对象
     */
    @Autowired
    WebSocketChannelInit webSocketChannelInit;

    /**
     * 连接线程组
     */
    private EventLoopGroup boos;
    /**
     * 处理事务线程组
     */
    private EventLoopGroup work;

    /**
     * 自定义启动方法
     * @param port
     */
    public void start(int port){
        //设置连接线程组
        boos = new NioEventLoopGroup(1);
        //设置处理线程组
        work = new NioEventLoopGroup();
        //创建启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boos,work)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(webSocketChannelInit);
        //绑定ip和端口 启动服务器
        ChannelFuture sync = null;
        try{
            //绑定netty的启动端口
            sync = serverBootstrap.bind(port).sync();
        }catch (InterruptedException e){
            log.error("tcp server start error", e);
            close();
        }
        log.info("netty server start success on port:" + port);
        sync.channel().closeFuture();
    }

    /**
     * 容器销毁前关闭线程组
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        close();
    }
    public void close(){
        if(boos!=null){
            boos.shutdownGracefully();
        }
        if(work!=null){
            work.shutdownGracefully();
        }
    }
}
