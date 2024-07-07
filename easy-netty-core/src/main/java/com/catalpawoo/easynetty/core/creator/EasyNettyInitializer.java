package com.catalpawoo.easynetty.core.creator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始化器抽象类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Slf4j
@AllArgsConstructor
public abstract class EasyNettyInitializer<C extends Channel> extends ChannelInitializer<C> {

    /**
     * 自定义处理方法
     */
    protected final SimpleChannelInboundHandler<?> simpleChannelInboundHandler;

    /**
     * 路径（请求地址）
     */
    protected final String path;

}
