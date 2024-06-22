package com.catalpawoo.easynetty.core.creator;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 服务端初始化类
 *
 * @author wuzijing
 * @since 2024-06-21
 */
public class EasyNettyServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 自定义处理方法
     */
    private final SimpleChannelInboundHandler<?> simpleChannelInboundHandler;

    /**
     * 请求路径
     */
    private final String websocketPath;

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param websocketPath               请求路径
     */
    public EasyNettyServerInitializer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String websocketPath) {
        this.simpleChannelInboundHandler = simpleChannelInboundHandler;
        this.websocketPath = websocketPath;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        // Websocket基于http协议，所以需要Http解码器
        channelPipeline.addLast(new HttpServerCodec());
        // 添加对于读写大数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        // 聚合HttpMessage支持
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 请求动作
        channelPipeline.addLast(new WebSocketServerProtocolHandler(websocketPath, null, true, 65536, true, true, 10000L));
        // 自定义处理方法
        if (ObjectUtil.isNotNull(this.simpleChannelInboundHandler)) {
            channelPipeline.addLast(simpleChannelInboundHandler);
        }
    }

}
