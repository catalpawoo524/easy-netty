package com.catalpawoo.easynetty.core.creator;

import com.catalpawoo.easynetty.common.builder.Builder;
import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import com.catalpawoo.easynetty.common.exceptions.EasyNettyException;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.charset.Charset;

/**
 * 服务端基础构造器
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class EasyNettyServerBuilder implements Builder<EasyNettyServerCreator> {

    /**
     * 自定义处理方法
     */
    private SimpleChannelInboundHandler<?> simpleChannelInboundHandler;

    /**
     * 请求路径
     */
    private String websocketPath;

    /**
     * 端口号
     */
    private Integer nettyPort;

    /**
     * 主线程组数量
     */
    private Integer bossThreadNum;

    /**
     * 一次性构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param websocketPath               请求路径
     * @param nettyPort                   端口号
     * @param bossThreadNum               主线程组数量
     * @return 服务端基础构造器
     */
    public static EasyNettyServerBuilder of(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String websocketPath, Integer nettyPort, Integer bossThreadNum) {
        return new EasyNettyServerBuilder(simpleChannelInboundHandler, websocketPath, nettyPort, bossThreadNum);
    }

    @Override
    public EasyNettyServerCreator build() {
        return new EasyNettyServerCreator(simpleChannelInboundHandler, websocketPath, nettyPort, bossThreadNum);
    }

}
