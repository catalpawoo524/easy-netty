package com.catalpawoo.easynetty.core.creator.client;

import com.catalpawoo.easynetty.common.constants.ErrorCodeConstant;
import com.catalpawoo.easynetty.common.utils.ExceptionUtil;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.creator.EasyNettyInitializer;
import com.catalpawoo.easynetty.core.properties.EasyNettyProperty;
import com.catalpawoo.easynetty.core.properties.client.EnHttpAggregatorProperty;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * 客户端初始化类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Slf4j
public class EasyNettyClientInitializer extends EasyNettyInitializer<SocketChannel> {

    /**
     * 是否安全（WSS协议）
     */
    private final Boolean safeStatus;

    /**
     * 构造方法
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        WebSocket请求地址
     * @param safeStatus                  是否安全（WSS协议）
     */
    public EasyNettyClientInitializer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Boolean safeStatus) {
        super(simpleChannelInboundHandler, path);
        this.safeStatus = safeStatus;
    }

    /**
     * 客户端连接初始化方法
     *
     * @param socketChannel 连接
     * @throws Exception 异常信息
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        URI websocketUri = parseUri(this.path);
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        if (this.safeStatus) {
            // 支持Wss协议
            SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            channelPipeline.addFirst(sslContext.newHandler(socketChannel.alloc(), websocketUri.getHost(), websocketUri.getPort()));
        }
        EasyNettyProperty easyNettyProperty = SpringUtil.getBean(EasyNettyProperty.class);
        // websocket基于http协议，所以需要http解码器
        channelPipeline.addLast(new HttpClientCodec());
        // 添加对于读写大数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        // 聚合HttpMessage支持
        EnHttpAggregatorProperty httpAggregator = easyNettyProperty.getClient().getHttpAggregator();
        channelPipeline.addLast(new HttpObjectAggregator(httpAggregator.getMaxLength()));
        // 启用WebSocket帧的压缩功能，从而减少数据传输的大小，提高通信效率
        channelPipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
        // 添加对于WebSocket的支持（握手）
        channelPipeline.addLast(new WebSocketClientProtocolHandler(WebSocketClientHandshakerFactory.newHandshaker(websocketUri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders())));
        // 自定义处理方法
        if (!ObjectUtil.isNull(this.simpleChannelInboundHandler)) {
            channelPipeline.addLast(simpleChannelInboundHandler);
        }
    }

    /**
     * 将路径解析为URI
     *
     * @param path 路径
     * @return URI
     */
    public static URI parseUri(String path) {
        URI uri;
        try {
            uri = new URI(path);
        } catch (Exception error) {
            throw ExceptionUtil.create(ErrorCodeConstant.CLIENT_STARTUP_URI_PARSING_EXCEPTION, path);
        }
        return uri;
    }

}
