package com.catalpawoo.easynetty.starter.handler;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础服务端自定义请求处理类
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class EasyNettyServerBaseHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(textWebSocketFrame)) {
            return;
        }
        log.info("easy-netty received message, content: {}.", textWebSocketFrame.text());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.put(channelHandlerContext.channel().id(), channelHandlerContext.channel());
        log.info("easy-netty add a new connection.");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        log.info("easy-netty event trigger: {}", event);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.remove(channelHandlerContext.channel().id());
        log.info("easy-netty remove a connection.");
    }

}
