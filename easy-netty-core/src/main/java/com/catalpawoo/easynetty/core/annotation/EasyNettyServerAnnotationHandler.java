package com.catalpawoo.easynetty.core.annotation;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 服务端基于注解的处理类
 *
 * @author wuzijing
 * @since 2024-06-24
 */
@Slf4j
@AllArgsConstructor
@ChannelHandler.Sharable
public class EasyNettyServerAnnotationHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 服务端实体类
     */
    private Object serverDO;

    /**
     * 读取方法
     */
    private Method readMethod;

    /**
     * 新增连接方法
     */
    private Method addMethod;

    /**
     * 事件触发方法
     */
    private Method triggerMethod;

    /**
     * 移除连接方法
     */
    private Method removeMethod;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(serverDO) || ObjectUtil.isNull(readMethod)) {
            return;
        }
        try {
            readMethod.invoke(serverDO, channelHandlerContext, textWebSocketFrame);
        } catch (Exception e) {
            log.error("easy-netty annotation-based read method execution failed.");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        if (ObjectUtil.isNull(serverDO) || ObjectUtil.isNull(addMethod)) {
            return;
        }
        try {
            addMethod.invoke(serverDO, channelHandlerContext);
        } catch (Exception e) {
            log.error("easy-netty annotation-based connection addition method execution failed.");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        if (ObjectUtil.isNull(serverDO) || ObjectUtil.isNull(triggerMethod)) {
            return;
        }
        try {
            triggerMethod.invoke(serverDO, channelHandlerContext, event);
        } catch (Exception e) {
            log.error("easy-netty annotation-based event trigger method execution failed.");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        if (ObjectUtil.isNull(serverDO) || ObjectUtil.isNull(removeMethod)) {
            return;
        }
        try {
            removeMethod.invoke(serverDO, channelHandlerContext);
        } catch (Exception e) {
            log.error("easy-netty annotation-based connection removal method execution failed.");
        }
    }

}
