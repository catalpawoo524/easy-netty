package com.catalpawoo.easynetty.core.creator.client;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端创建器
 *
 * @author wuzijing
 * @since 2024-07-07
 */
@Slf4j
public class EasyNettyClientCreator extends AbstractEasyNettyClient {

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param safeStatus                  是否安全
     */
    public EasyNettyClientCreator(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Boolean safeStatus) {
        super(simpleChannelInboundHandler, path, safeStatus);
    }

    /**
     * 构造方法
     *
     * @param path                       路径
     * @param safeStatus                 是否安全
     * @param easyNettyClientInitializer 客户端初始化类
     */
    public EasyNettyClientCreator(String path, Boolean safeStatus, EasyNettyClientInitializer easyNettyClientInitializer) {
        super(easyNettyClientInitializer, path, safeStatus);
    }

    /**
     * 构造方法
     *
     * @param bootstrap  启动器
     * @param path       请求路径
     * @param safeStatus 是否安全
     */
    public EasyNettyClientCreator(Bootstrap bootstrap, String path, Boolean safeStatus) {
        super(bootstrap, path, safeStatus);
    }

    @Override
    public EasyNettyClientCreator shutdown() {
        if (ObjectUtil.isNotNull(this.channel) && this.channel.isOpen()) {
            this.channel.close();
        }
        this.shutdownThreadGroup();
        return this;
    }

}
