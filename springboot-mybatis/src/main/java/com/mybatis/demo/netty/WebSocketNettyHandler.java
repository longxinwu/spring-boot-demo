package com.mybatis.demo.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@Data
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketNettyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 存储用户对应的通道
     */
    Map<String, ChannelHandlerContext> MAP = new ConcurrentHashMap<>(16);
    /**
     * 存放通道和用户关联
     */
    Map<String, String> CHANNEL_USER = new ConcurrentHashMap<>(16);
    /**
     * 存储当前链接上的通道
     */
    List<ChannelHandlerContext> channelList = new CopyOnWriteArrayList<>();

    /**
     * 通道连接事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        channelList.add(ctx);
        log.info("有新的链接，当前链接数：" + channelList.size());
    }

    /**
     * 通道消息事件
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("前端发来的消息：" + textWebSocketFrame.text());
        Message msg = JSON.parseObject(textWebSocketFrame.text(),Message.class);
        if(msg.getType() == 1){
            setMap(channelHandlerContext, msg);
            //给其他服务器发送上线消息
            for(ChannelHandlerContext context : MAP.values()){
                if(context == channelHandlerContext){
                    continue;
                }
                context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
            }
            return;
        }
        // 获取到需要转发的客户端
        String receive = msg.getReceive();
        //没有指定接收者代表要群发
        if(StringUtil.isNullOrEmpty(receive)){
            for(ChannelHandlerContext context : MAP.values()){
                if(context == channelHandlerContext){
                    continue;
                }
                context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
            }
        }
        //存缓存的存储用户对应的通道 map中获取
        /*if(!MAP.containsKey(receive)){
            Message message = new Message("服务端", channelHandlerContext.name(), UUID.randomUUID().toString(), "用户未在线， 你的消息不能及时送达", 2);
            channelHandlerContext.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
            return;
        }
        //服务端转发消息到指定的客户端
        ChannelHandlerContext handlerContext = MAP.get(receive);
        handlerContext.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));*/
    }

    /**
     * 设置连接映射
     * @param context
     * @param message
     */
    private void setMap(ChannelHandlerContext context, Message message){
        MAP.put(message.getSend(), context);
        CHANNEL_USER.put(context.channel().id().toString(), message.getSend());
    }

    /**
     * 通达关闭事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //super.channelInactive(ctx);
        String s = CHANNEL_USER.get(ctx.channel().id().toString());
        MAP.remove(s);
        //给其他在线用户发送该用户离线消息
        for(ChannelHandlerContext context : MAP.values()){
            Message message = new Message("服务端", null, UUID.randomUUID().toString(), "用户--"+s+"--已经离线", 2);
            context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
        }
        channelList.remove(ctx);
        CHANNEL_USER.remove(ctx.channel().id().toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        String s = CHANNEL_USER.get(ctx.channel().id().toString());
        MAP.remove(s);
        //给其他在线用户发送该用户离线的消息
        for(ChannelHandlerContext context : MAP.values()){
            Message message = new Message("服务器", null, UUID.randomUUID().toString(), "用户--"+s+"--连接发生问题，已被迫离线",2);
            context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
        }
        channelList.remove(ctx);
        CHANNEL_USER.remove(ctx.channel().id().toString());
    }
}
