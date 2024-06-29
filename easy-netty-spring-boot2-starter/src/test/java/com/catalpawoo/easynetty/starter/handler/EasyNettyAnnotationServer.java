package com.catalpawoo.easynetty.starter.handler;

import com.catalpawoo.easynetty.annotation.*;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 基于注解的Easy-Netty服务端测试
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@Component
@EnServer(path = "/socket", port = 8081)
public class EasyNettyAnnotationServer {

    @PostConstruct
    private void init() {
        log.info("easy-netty server initialization.");
    }

    @EnRead
    public void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(textWebSocketFrame)) {
            return;
        }
        log.info("easy-netty received message, content: {}.", textWebSocketFrame.text());
    }

    @EnAdd
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        log.info("easy-netty add a new connection.");
    }

    @EnEventTrigger
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        log.info("easy-netty event trigger: {}", event);
    }

    @EnRemove
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        log.info("easy-netty remove a connection.");
    }

}
