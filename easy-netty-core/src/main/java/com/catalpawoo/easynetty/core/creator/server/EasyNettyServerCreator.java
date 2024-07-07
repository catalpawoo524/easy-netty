package com.catalpawoo.easynetty.core.creator.server;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端创建器
 *
 * @author wuzijing
 * @since 2024-06-18
 */
@Slf4j
public class EasyNettyServerCreator extends AbstractEasyNettyServer {

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     */
    public EasyNettyServerCreator(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        super(simpleChannelInboundHandler, path, port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port                       端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务端初始化类
     */
    public EasyNettyServerCreator(Integer port, Integer bossThreadNum, EasyNettyServerInitializer easyNettyServerInitializer) {
        super(port, bossThreadNum, easyNettyServerInitializer);
    }

    /**
     * 构造方法
     *
     * @param port            端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap Netty服务端启动器
     */
    public EasyNettyServerCreator(Integer port, Integer bossThreadNum, ServerBootstrap serverBootstrap) {
        super(port, bossThreadNum, serverBootstrap);
    }

    @Override
    public EasyNettyServerCreator shutdown() {
        if (ObjectUtil.isNotNull(this.channel) && this.channel.isOpen()) {
            this.channel.close();
        }
        this.shutdownThreadGroup();
        return this;
    }

    @Override
    public void shutdownThreadGroup() {
        if (ObjectUtil.isNotNull(this.bossGroup)) {
            this.bossGroup.shutdownGracefully();
        }
        super.shutdownThreadGroup();
    }

}
