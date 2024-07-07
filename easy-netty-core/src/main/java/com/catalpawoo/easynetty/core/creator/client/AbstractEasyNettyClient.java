package com.catalpawoo.easynetty.core.creator.client;

import com.catalpawoo.easynetty.common.constants.ErrorCodeConstant;
import com.catalpawoo.easynetty.common.utils.ExceptionUtil;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.common.utils.StrUtil;
import com.catalpawoo.easynetty.core.creator.AbstractEasyNetty;
import com.catalpawoo.easynetty.core.events.EnClientCreateEvent;
import com.catalpawoo.easynetty.core.properties.EasyNettyProperty;
import com.catalpawoo.easynetty.core.properties.client.EnCreatorProperty;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * 客户端创建器抽象类
 *
 * @author wuzijing
 * @since 2024-07-07
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractEasyNettyClient extends AbstractEasyNetty {

    /**
     * 是否安全
     */
    protected Boolean safeStatus;

    /**
     * 检查参数
     *
     * @param path       路径
     * @param safeStatus 是否安全
     */
    private void checkParam(String path, Boolean safeStatus) {
        if (ObjectUtil.isNull(safeStatus) || StrUtil.isNullOrUndefined(path)) {
            // 参数缺失
            throw ExceptionUtil.create(ErrorCodeConstant.CLIENT_STARTUP_MISSING_PARAMETERS);
        }
        this.workGroup = new NioEventLoopGroup();
        this.safeStatus = safeStatus;
    }

    /**
     * 启动客户端
     *
     * @param path      路径
     * @param bootstrap 启动器
     */
    private void start(String path, @NonNull Bootstrap bootstrap) {
        URI websocketUri = EasyNettyClientInitializer.parseUri(path);
        ChannelFuture channelFuture;
        try {
            channelFuture = bootstrap.connect(new InetSocketAddress(websocketUri.getHost(), websocketUri.getPort()));
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            throw ExceptionUtil.create(ErrorCodeConstant.CLIENT_STARTUP_CONNECT_EXCEPTION, path);
        }
        if (!channelFuture.isSuccess()) {
            throw ExceptionUtil.create(ErrorCodeConstant.CLIENT_STARTUP_CONNECT_FAILED, path);
        }
        log.info("easy-netty client started successfully，parameters: path={}.", path);
        SpringUtil.publishEvent(new EnClientCreateEvent(this));
    }

    /**
     * 创建启动器
     *
     * @param easyNettyClientInitializer 客户端初始化类
     * @return 启动器
     */
    private Bootstrap createBootstrap(@NonNull EasyNettyClientInitializer easyNettyClientInitializer) {
        EnCreatorProperty creatorProperty = SpringUtil.getBean(EasyNettyProperty.class).getClient().getCreator();
        return new Bootstrap()
                .group(this.workGroup)
                .channel(NioSocketChannel.class)
                // 低延迟
                .option(ChannelOption.TCP_NODELAY, creatorProperty.getTcpNoDelay())
                // 快速复用
                .option(ChannelOption.SO_REUSEADDR, creatorProperty.getSoReuseaddr())
                // 自定义处理方法
                .handler(easyNettyClientInitializer);
    }

    /**
     * 启动服务端
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param safeStatus                  是否安全
     */
    public AbstractEasyNettyClient(@NonNull SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Boolean safeStatus) {
        this.checkParam(path, safeStatus);
        this.start(path, this.createBootstrap(new EasyNettyClientInitializer(simpleChannelInboundHandler, path, this.safeStatus)));
    }

    /**
     * 启动服务端
     *
     * @param easyNettyClientInitializer 客户端初始化器
     * @param path                       请求路径
     * @param safeStatus                 是否安全
     */
    public AbstractEasyNettyClient(@NonNull EasyNettyClientInitializer easyNettyClientInitializer, String path, Boolean safeStatus) {
        this.checkParam(path, safeStatus);
        this.start(path, this.createBootstrap(easyNettyClientInitializer));
    }

    /**
     * 启动服务端
     *
     * @param bootstrap  启动器
     * @param path       请求路径
     * @param safeStatus 是否安全
     */
    public AbstractEasyNettyClient(@NonNull Bootstrap bootstrap, String path, Boolean safeStatus) {
        this.checkParam(path, safeStatus);
        this.start(path, bootstrap);
    }

}