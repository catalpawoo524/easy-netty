package com.catalpawoo.easynetty.core.creator;

import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import com.catalpawoo.easynetty.common.exceptions.EasyNettyException;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端创建器
 *
 * @author wuzijing
 * @since 2024-06-18
 */
@Slf4j
@Setter
public class EasyNettyServerCreator {

    /**
     * 接受请求的线程组
     */
    private NioEventLoopGroup bossGroup;

    /**
     * 实际工作的线程组
     */
    private NioEventLoopGroup workGroup;

    /**
     * 当前连接
     */
    @Getter
    private Channel channel;

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     */
    public EasyNettyServerCreator(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        if (ObjectUtil.isNull(port) || ObjectUtil.isNull(bossThreadNum)) {
            // 参数缺失
            throw new EasyNettyException("easy-netty server fail to start, the input parameters are missing.");
        }
        this.bossGroup = new NioEventLoopGroup(bossThreadNum);
        this.workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                // 连接请求等待队列大小
                .option(ChannelOption.SO_BACKLOG, IntegerConstant.ONE_THOUSAND_TWENTY_FOUR)
                // 快速复用，防止服务端重启端口占用
                .option(ChannelOption.SO_REUSEADDR, true)
                // 自定义处理方法
                .childHandler(new EasyNettyServerInitializer(simpleChannelInboundHandler, path))
                // 低延迟
                .childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定端口号
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            log.error("easy-netty server startup failed, port number binding exception, parameters: port={}，bossThreadNum={}.", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding exception.");
        }
        if (!channelFuture.isSuccess()) {
            log.error("easy-netty server startup failed, port number binding failed，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding failed.");
        }
        log.info("easy-netty server started successfully，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port                       端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务初始化类
     */
    public EasyNettyServerCreator(Integer port, Integer bossThreadNum, EasyNettyServerInitializer easyNettyServerInitializer) {
        if (ObjectUtil.isNull(port) || ObjectUtil.isNull(bossThreadNum)) {
            // 参数缺失
            throw new EasyNettyException("easy-netty server fail to start, the input parameters are missing.");
        }
        this.bossGroup = new NioEventLoopGroup(bossThreadNum);
        this.workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                // 连接请求等待队列大小
                .option(ChannelOption.SO_BACKLOG, IntegerConstant.ONE_THOUSAND_TWENTY_FOUR)
                // 快速复用，防止服务端重启端口占用
                .option(ChannelOption.SO_REUSEADDR, true)
                // 自定义处理方法
                .childHandler(easyNettyServerInitializer)
                // 低延迟
                .childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定端口号
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            log.error("easy-netty server startup failed, port number binding exception, parameters: port={}，bossThreadNum={}.", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding exception.");
        }
        if (!channelFuture.isSuccess()) {
            log.error("easy-netty server startup failed, port number binding failed，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding failed.");
        }
        log.info("easy-netty server started successfully，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port            端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap Netty服务端启动器
     */
    public EasyNettyServerCreator(Integer port, Integer bossThreadNum, ServerBootstrap serverBootstrap) {
        if (ObjectUtil.isNull(port) || ObjectUtil.isNull(bossThreadNum)) {
            // 参数缺失
            throw new EasyNettyException("easy-netty server fail to start, the input parameters are missing.");
        }
        this.bossGroup = new NioEventLoopGroup(bossThreadNum);
        this.workGroup = new NioEventLoopGroup();
        // 绑定端口号
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            log.error("easy-netty server startup failed, port number binding exception, parameters: port={}，bossThreadNum={}.", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding exception.");
        }
        if (!channelFuture.isSuccess()) {
            log.error("easy-netty server startup failed, port number binding failed，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
            throw new EasyNettyException("easy-netty server startup failed, port number binding failed.");
        }
        log.info("easy-netty server started successfully，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
    }

    /**
     * 关闭方法
     *
     * @return 自身
     */
    public EasyNettyServerCreator shutdown() {
        if (ObjectUtil.isNotNull(this.channel) && this.channel.isOpen()) {
            this.channel.close();
        }
        if (ObjectUtil.isNotNull(this.bossGroup)) {
            this.bossGroup.shutdownGracefully();
        }
        if (ObjectUtil.isNotNull(this.workGroup)) {
            this.workGroup.shutdownGracefully();
        }
        return this;
    }

    /**
     * 线程组关闭方法
     */
    public void shutdownThreadGroup() {
        if (ObjectUtil.isNotNull(this.bossGroup)) {
            this.bossGroup.shutdownGracefully();
        }
        if (ObjectUtil.isNotNull(this.workGroup)) {
            this.workGroup.shutdownGracefully();
        }
    }

    /**
     * 连接是否开启
     *
     * @return 开启状态（true：打开，false：关闭）
     */
    public boolean isOpen() {
        if (ObjectUtil.isNull(this.channel)) {
            return false;
        }
        return this.channel.isOpen();
    }

    /**
     * 连接是否关闭
     *
     * @return 关闭状态（true：关闭，false：打开）
     */
    public boolean isStop() {
        return !this.isOpen();
    }

}
