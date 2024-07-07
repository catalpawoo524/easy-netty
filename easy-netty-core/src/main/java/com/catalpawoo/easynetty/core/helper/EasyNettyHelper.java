package com.catalpawoo.easynetty.core.helper;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import com.catalpawoo.easynetty.core.creator.AbstractEasyNetty;
import com.catalpawoo.easynetty.core.creator.client.AbstractEasyNettyClient;
import com.catalpawoo.easynetty.core.creator.server.AbstractEasyNettyServer;
import com.catalpawoo.easynetty.core.creator.server.EasyNettyServerCreator;
import com.catalpawoo.easynetty.core.creator.server.EasyNettyServerInitializer;
import com.catalpawoo.easynetty.core.events.EnClientCreateEvent;
import com.catalpawoo.easynetty.core.events.EnServerCreateEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 快速开始帮助类
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Slf4j
public class EasyNettyHelper {

    /**
     * 创建器集合
     */
    private final Map<ChannelId, AbstractEasyNetty> creatorMap = new ConcurrentHashMap<>();

    /**
     * 服务端创建事件监听
     *
     * @param event 服务端创建事件
     */
    @Async
    @EventListener(EnServerCreateEvent.class)
    protected void serverCreateListener(EnServerCreateEvent event) {
        log.info("easy-netty listen to the server creation event.");
        if (ObjectUtil.isNull(event)) {
            return;
        }
        if (event.getSource() instanceof AbstractEasyNettyServer) {
            AbstractEasyNettyServer creator = (AbstractEasyNettyServer) event.getSource();
            Channel channel = creator.getChannel();
            if (ObjectUtil.isNull(channel)) {
                return;
            }
            creatorMap.put(channel.id(), creator);
        }
    }

    /**
     * 客户端创建事件监听
     *
     * @param event 服务端创建事件
     */
    @Async
    @EventListener(EnClientCreateEvent.class)
    protected void clientCreateListener(EnClientCreateEvent event) {
        log.info("easy-netty listen to the client creation event.");
        if (ObjectUtil.isNull(event)) {
            return;
        }
        if (event.getSource() instanceof AbstractEasyNettyClient) {
            AbstractEasyNettyClient creator = (AbstractEasyNettyClient) event.getSource();
            Channel channel = creator.getChannel();
            if (ObjectUtil.isNull(channel)) {
                return;
            }
            creatorMap.put(channel.id(), creator);
        }
    }

    /**
     * 启动服务端
     *
     * @param simpleChannelInboundHandler 自定义处理方法
     * @param path                        请求路径
     * @param port                        端口号
     * @param bossThreadNum               主线程组数量
     * @return 连接ID
     */
    public ChannelId startServer(SimpleChannelInboundHandler<?> simpleChannelInboundHandler, String path, Integer port, Integer bossThreadNum) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(simpleChannelInboundHandler, path, port, bossThreadNum);
        Channel channel = serverCreator.getChannel();
        return channel.id();
    }

    /**
     * 启动服务端
     *
     * @param port                       端口号
     * @param bossThreadNum              主线程组数量
     * @param easyNettyServerInitializer 服务初始化类
     * @return 连接ID
     */
    public ChannelId startServer(Integer port, Integer bossThreadNum, EasyNettyServerInitializer easyNettyServerInitializer) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(port, bossThreadNum, easyNettyServerInitializer);
        Channel channel = serverCreator.getChannel();
        return channel.id();
    }

    /**
     * 启动服务端
     *
     * @param port            端口号
     * @param bossThreadNum   主线程组数量
     * @param serverBootstrap 服务端启动器
     * @return 连接ID
     */
    public ChannelId startServer(Integer port, Integer bossThreadNum, ServerBootstrap serverBootstrap) {
        EasyNettyServerCreator serverCreator = new EasyNettyServerCreator(port, bossThreadNum, serverBootstrap);
        Channel channel = serverCreator.getChannel();
        return channel.id();
    }

    /**
     * 停止
     *
     * @param channelId 连接ID
     * @return 停止结果（true：成功，false：失败）
     */
    public boolean shutdown(ChannelId channelId) {
        if (!this.creatorMap.containsKey(channelId)) {
            log.error("easy-netty server failed to stop, connection ID does not exist.");
            return false;
        }
        AbstractEasyNetty creator = this.creatorMap.get(channelId).shutdown();
        this.creatorMap.remove(channelId);
        return creator.isStop();
    }

    /**
     * 停止
     *
     * @return 停止数量
     */
    public int shutdown() {
        ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        // 关闭连接与线程组
        this.creatorMap.forEach(((channelId, creator) -> {
            channelGroup.add(creator.getChannel());
            creator.shutdownThreadGroup();
        }));
        channelGroup.close();
        // 检查关闭状态
        Set<ChannelId> closeIdSet = new HashSet<>();
        this.creatorMap.forEach(((channelId, serverCreator) -> {
            if (serverCreator.isStop()) {
                closeIdSet.add(channelId);
            }
        }));
        closeIdSet.forEach(this.creatorMap::remove);
        return closeIdSet.size();
    }

}
