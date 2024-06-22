package com.catalpawoo.easynetty.starter.handler;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.creator.EasyNettyServerCreator;
import com.catalpawoo.easynetty.core.creator.EasyNettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Easy-Netty处理类
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
@Configuration
public class EasyNettyHandler {

    /**
     * 服务端集合
     */
    private final Map<ChannelId, EasyNettyServerCreator> serverCreatorMap = new ConcurrentHashMap<>();

    /**
     * 启动服务端
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param websocketPath               请求路径
     * @param nettyPort                   端口号
     * @param bossThreadNum               主线程组数量
     * @return 连接ID
     */
    public ChannelId startNettyServer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String websocketPath, Integer nettyPort, Integer bossThreadNum) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(simpleChannelInboundHandler, websocketPath, nettyPort, bossThreadNum);
        Channel channel = serverCreator.getChannel();
        this.serverCreatorMap.put(channel.id(), serverCreator);
        return channel.id();
    }

    /**
     * 启动服务端
     *
     * @param nettyPort                  端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务初始化类
     * @return 连接ID
     */
    public ChannelId startNettyServer(Integer nettyPort, Integer bossThreadNum, EasyNettyServerInitializer easyNettyServerInitializer) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(nettyPort, bossThreadNum, easyNettyServerInitializer);
        Channel channel = serverCreator.getChannel();
        this.serverCreatorMap.put(channel.id(), serverCreator);
        return channel.id();
    }

    /**
     * 启动服务端
     *
     * @param nettyPort       端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap 服务端启动器
     * @return 连接ID
     */
    public ChannelId startNettyServer(Integer nettyPort, Integer bossThreadNum, ServerBootstrap serverBootstrap) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(nettyPort, bossThreadNum, serverBootstrap);
        Channel channel = serverCreator.getChannel();
        this.serverCreatorMap.put(channel.id(), serverCreator);
        return channel.id();
    }

    /**
     * 停止服务端
     *
     * @param channelId 连接ID
     * @return 停止结果（true：成功，false：失败）
     */
    public boolean shutdownNettyServer(ChannelId channelId) {
        if (!this.serverCreatorMap.containsKey(channelId)) {
            log.error("easy-netty server failed to stop, connection ID does not exist.");
            return false;
        }
        EasyNettyServerCreator serverCreator = this.serverCreatorMap.get(channelId).shutdown();
        this.serverCreatorMap.remove(channelId);
        return serverCreator.isStop();
    }

    /**
     * 停止所有服务端
     *
     * @return 停止数量
     */
    public int shutdownNettyServer() {
        ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        // 关闭连接与线程组
        this.serverCreatorMap.forEach(((channelId, serverCreator) -> {
            channelGroup.add(serverCreator.getChannel());
            serverCreator.shutdownThreadGroup();
        }));
        channelGroup.close();
        // 检查关闭状态
        Set<ChannelId> closeIdSet = new HashSet<>();
        this.serverCreatorMap.forEach(((channelId, serverCreator) -> {
            if (serverCreator.isStop()) {
                closeIdSet.add(channelId);
            }
        }));
        closeIdSet.forEach(this.serverCreatorMap::remove);
        return closeIdSet.size();
    }

    /**
     * 存入主动构造的服务端
     *
     * @param easyNettyServerCreator 服务端创建器
     * @return 连接ID
     */
    public ChannelId putServer(EasyNettyServerCreator easyNettyServerCreator) {
        if (ObjectUtil.isNull(easyNettyServerCreator)) {
            return null;
        }
        ChannelId channelId = easyNettyServerCreator.getChannel().id();
        this.serverCreatorMap.put(channelId, easyNettyServerCreator);
        return channelId;
    }

}
