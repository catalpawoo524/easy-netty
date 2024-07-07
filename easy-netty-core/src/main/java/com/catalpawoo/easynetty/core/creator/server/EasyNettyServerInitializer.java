package com.catalpawoo.easynetty.core.creator.server;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.creator.EasyNettyInitializer;
import com.catalpawoo.easynetty.core.properties.EasyNettyProperty;
import com.catalpawoo.easynetty.core.properties.server.EnHttpAggregatorProperty;
import com.catalpawoo.easynetty.core.properties.server.EnProtocolHandlerProperty;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
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
public class EasyNettyServerInitializer extends EasyNettyInitializer<SocketChannel> {

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     */
    public EasyNettyServerInitializer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path) {
        super(simpleChannelInboundHandler, path);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        EasyNettyProperty easyNettyProperty = SpringUtil.getBean(EasyNettyProperty.class);
        // Websocket基于http协议，所以需要Http解码器
        channelPipeline.addLast(new HttpServerCodec());
        // 添加对于读写大数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        // 聚合HttpMessage支持
        EnHttpAggregatorProperty httpAggregatorProperty = easyNettyProperty.getServer().getHttpAggregator();
        channelPipeline.addLast(new HttpObjectAggregator(httpAggregatorProperty.getMaxLength()));
        // 请求动作
        EnProtocolHandlerProperty protocolHandlerProperty = easyNettyProperty.getServer().getProtocolHandler();
        channelPipeline.addLast(new WebSocketServerProtocolHandler(path, null, protocolHandlerProperty.getAllowExtensions(), protocolHandlerProperty.getMaxFrameSize(), protocolHandlerProperty.getAllowMaskMismatch(), protocolHandlerProperty.getCheckStartsWith(), protocolHandlerProperty.getHandshakeTimeoutMillis()));
        // 自定义处理方法
        if (ObjectUtil.isNotNull(this.simpleChannelInboundHandler)) {
            channelPipeline.addLast(simpleChannelInboundHandler);
        }
    }

}
