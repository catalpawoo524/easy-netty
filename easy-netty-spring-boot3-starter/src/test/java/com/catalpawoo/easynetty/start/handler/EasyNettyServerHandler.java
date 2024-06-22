package com.catalpawoo.easynetty.start.handler;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Easy-Netty服务端自定义请求处理类
 *
 * @author wuzijing
 * @apiNote Easy-Netty服务端自定义请求处理类
 * @since 2024-06-22
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class EasyNettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(textWebSocketFrame)) {
            return;
        }
        log.info(textWebSocketFrame.text());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.put(channelHandlerContext.channel().id(), channelHandlerContext.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete complete) {
            log.info("easy-netty hand shake.");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.remove(channelHandlerContext.channel().id());
    }

}
