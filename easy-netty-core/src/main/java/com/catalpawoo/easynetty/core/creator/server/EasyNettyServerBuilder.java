package com.catalpawoo.easynetty.core.creator.server;

import com.catalpawoo.easynetty.common.builder.Builder;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端基础构造器
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class EasyNettyServerBuilder implements Builder<EasyNettyServerCreator> {

    /**
     * 自定义处理方法
     */
    private SimpleChannelInboundHandler<?> simpleChannelInboundHandler;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 主线程组数量
     */
    private Integer bossThreadNum;

    /**
     * 设置自定义处理方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @return 服务端基础构造器
     */
    public EasyNettyServerBuilder setHandler(SimpleChannelInboundHandler<?> simpleChannelInboundHandler) {
        this.simpleChannelInboundHandler = simpleChannelInboundHandler;
        return this;
    }

    /**
     * 设置路径
     *
     * @param path 请求路径
     * @return 服务端基础构造器
     */
    public EasyNettyServerBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * 设置端口
     *
     * @param port 端口
     * @return 服务端基础构造器
     */
    public EasyNettyServerBuilder setPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * 设置主线程数量
     *
     * @param bossThreadNum 主线程数量
     * @return 服务端基础构造器
     */
    public EasyNettyServerBuilder setBossThreadNum(Integer bossThreadNum) {
        this.bossThreadNum = bossThreadNum;
        return this;
    }

    /**
     * 一次性构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     * @return 服务端基础构造器
     */
    public static EasyNettyServerBuilder of(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        return new EasyNettyServerBuilder(simpleChannelInboundHandler, path, port, bossThreadNum);
    }

    @Override
    public EasyNettyServerCreator build() {
        return new EasyNettyServerCreator(simpleChannelInboundHandler, path, port, bossThreadNum);
    }

}
