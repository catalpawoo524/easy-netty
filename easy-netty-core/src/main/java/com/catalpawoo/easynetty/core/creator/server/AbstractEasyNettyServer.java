package com.catalpawoo.easynetty.core.creator.server;

import com.catalpawoo.easynetty.common.constants.ErrorCodeConstant;
import com.catalpawoo.easynetty.common.utils.ExceptionUtil;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.creator.AbstractEasyNetty;
import com.catalpawoo.easynetty.core.events.EnServerCreateEvent;
import com.catalpawoo.easynetty.core.properties.EasyNettyProperty;
import com.catalpawoo.easynetty.core.properties.server.EnCreatorProperty;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端创建器抽象类
 *
 * @author wuzijing
 * @since 2024-07-01
 */
@Slf4j
@Setter
public abstract class AbstractEasyNettyServer extends AbstractEasyNetty {

    /**
     * 接受请求的线程组
     */
    protected NioEventLoopGroup bossGroup;

    /**
     * 检查参数
     *
     * @param port          端口号
     * @param bossThreadNum 主线程组数量
     */
    private void checkParam(Integer port, Integer bossThreadNum) {
        if (ObjectUtil.isNull(port) || ObjectUtil.isNull(bossThreadNum)) {
            // 参数缺失
            throw ExceptionUtil.create(ErrorCodeConstant.SERVER_STARTUP_MISSING_PARAMETERS);
        }
        this.bossGroup = new NioEventLoopGroup(bossThreadNum);
        this.workGroup = new NioEventLoopGroup();
    }

    /**
     * 启动服务端
     *
     * @param serverBootstrap Netty服务端启动器
     * @param port            端口
     * @param bossThreadNum   主线程数量
     */
    private void start(@NonNull ServerBootstrap serverBootstrap, Integer port, Integer bossThreadNum) {
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            throw ExceptionUtil.create(ErrorCodeConstant.SERVER_STARTUP_PORT_BIND_EXCEPTION, port);
        }
        if (!channelFuture.isSuccess()) {
            throw ExceptionUtil.create(ErrorCodeConstant.SERVER_STARTUP_PORT_BIND_FAILED, port);
        }
        log.info("easy-netty server started successfully，parameters: port={}，bossThreadNum={}.", port, bossThreadNum);
        SpringUtil.publishEvent(new EnServerCreateEvent(this));
    }

    /**
     * 创建Netty服务端启动器
     *
     * @param easyNettyServerInitializer 服务端初始化类
     * @return Netty服务端启动器
     */
    private ServerBootstrap createBootstrap(@NonNull EasyNettyServerInitializer easyNettyServerInitializer) {
        EnCreatorProperty creatorProperty = SpringUtil.getBean(EasyNettyProperty.class).getServer().getCreator();
        return new ServerBootstrap()
                .group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                // 连接请求等待队列大小
                .option(ChannelOption.SO_BACKLOG, creatorProperty.getSoBacklog())
                // 快速复用，防止服务端重启端口占用
                .option(ChannelOption.SO_REUSEADDR, creatorProperty.getSoReuseaddr())
                // 自定义处理方法
                .childHandler(easyNettyServerInitializer)
                // 低延迟
                .childOption(ChannelOption.TCP_NODELAY, creatorProperty.getTcpNoDelay());
    }

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     */
    public AbstractEasyNettyServer(@NonNull SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        this.checkParam(port, bossThreadNum);
        this.start(this.createBootstrap(new EasyNettyServerInitializer(simpleChannelInboundHandler, path)), port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port                       端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务初始化类
     */
    public AbstractEasyNettyServer(Integer port, Integer bossThreadNum, @NonNull EasyNettyServerInitializer easyNettyServerInitializer) {
        this.checkParam(port, bossThreadNum);
        this.start(this.createBootstrap(easyNettyServerInitializer), port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port            端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap Netty服务端启动器
     */
    public AbstractEasyNettyServer(Integer port, Integer bossThreadNum, @NonNull ServerBootstrap serverBootstrap) {
        this.checkParam(port, bossThreadNum);
        this.start(serverBootstrap, port, bossThreadNum);
    }

}
