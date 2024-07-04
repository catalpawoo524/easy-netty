package com.catalpawoo.easynetty.core;

import com.catalpawoo.easynetty.common.constants.ErrorCodeConstant;
import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import com.catalpawoo.easynetty.common.exceptions.EasyNettyException;
import com.catalpawoo.easynetty.common.utils.ExceptionUtil;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.events.EnServerCreateEvent;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
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
public abstract class AbstractEasyNettyServer {

    /**
     * 接受请求的线程组
     */
    protected NioEventLoopGroup bossGroup;

    /**
     * 实际工作的线程组
     */
    protected NioEventLoopGroup workGroup;

    /**
     * 当前连接
     */
    @Getter
    protected Channel channel;

    /**
     * 检查参数
     *
     * @param port          端口号
     * @param bossThreadNum 主线程组数量
     */
    private void checkParam(Integer port, Integer bossThreadNum) {
        if (ObjectUtil.isNull(port) || ObjectUtil.isNull(bossThreadNum)) {
            // 参数缺失
            throw ExceptionUtil.create(ErrorCodeConstant.STARTUP_MISSING_PARAMETERS);
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
    private void start(ServerBootstrap serverBootstrap, Integer port, Integer bossThreadNum) {
        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channel = channelFuture.sync().channel();
        } catch (Exception error) {
            throw ExceptionUtil.create(ErrorCodeConstant.STARTUP_PORT_BIND_EXCEPTION);
        }
        if (!channelFuture.isSuccess()) {
            throw ExceptionUtil.create(ErrorCodeConstant.STARTUP_PORT_BIND_FAILED);
        }
        log.info("easy-netty server started successfully，parameters: port={}，bossThreadNum={}", port, bossThreadNum);
        SpringUtil.publishEvent(new EnServerCreateEvent(this));
    }

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     */
    public AbstractEasyNettyServer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        this.checkParam(port, bossThreadNum);
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
        this.start(serverBootstrap, port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port                       端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务初始化类
     */
    public AbstractEasyNettyServer(Integer port, Integer bossThreadNum, EasyNettyServerInitializer easyNettyServerInitializer) {
        this.checkParam(port, bossThreadNum);
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
        this.start(serverBootstrap, port, bossThreadNum);
    }

    /**
     * 构造方法
     *
     * @param port            端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap Netty服务端启动器
     */
    public AbstractEasyNettyServer(Integer port, Integer bossThreadNum, ServerBootstrap serverBootstrap) {
        this.checkParam(port, bossThreadNum);
        this.start(serverBootstrap, port, bossThreadNum);
    }

    /**
     * 关闭方法
     *
     * @return 自身
     */
    public abstract AbstractEasyNettyServer shutdown();

    /**
     * 线程组关闭方法
     */
    public abstract void shutdownThreadGroup();

    /**
     * 连接是否开启
     *
     * @return 开启状态（true：打开，false：关闭）
     */
    public abstract boolean isOpen();

    /**
     * 连接是否关闭
     *
     * @return 关闭状态（true：关闭，false：打开）
     */
    public abstract boolean isStop();

}
