package com.catalpawoo.easynetty.core.creator.client;

import com.catalpawoo.easynetty.common.builder.Builder;
import com.catalpawoo.easynetty.core.creator.server.EasyNettyServerCreator;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端基础构造器
 *
 * @author wuzijing
 * @since 2024-07-07
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class EasyNettyClientBuilder implements Builder<EasyNettyClientCreator> {

    /**
     * 自定义处理方法
     */
    private SimpleChannelInboundHandler<?> simpleChannelInboundHandler;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 是否安全
     */
    private Boolean safeStatus;

    /**
     * 设置自定义处理方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @return 客户端基础构造器
     */
    public EasyNettyClientBuilder setHandler(SimpleChannelInboundHandler<?> simpleChannelInboundHandler) {
        this.simpleChannelInboundHandler = simpleChannelInboundHandler;
        return this;
    }

    /**
     * 设置路径
     *
     * @param path 请求路径
     * @return 客户端基础构造器
     */
    public EasyNettyClientBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * 设置是否安全
     *
     * @param safeStatus 是否安全
     * @return 客户端基础构造器
     */
    public EasyNettyClientBuilder setSafeStatus(Boolean safeStatus) {
        this.safeStatus = safeStatus;
        return this;
    }

    /**
     * 一次性构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param safeStatus                  是否安全
     * @return 客户端基础构造器
     */
    public static EasyNettyClientBuilder of(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Boolean safeStatus) {
        return new EasyNettyClientBuilder(simpleChannelInboundHandler, path, safeStatus);
    }

    @Override
    public EasyNettyClientCreator build() {
        return new EasyNettyClientCreator(simpleChannelInboundHandler, path, safeStatus);
    }

}
