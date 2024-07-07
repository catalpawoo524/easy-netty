package com.catalpawoo.easynetty.core.creator;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建器抽象类
 *
 * @author wuzijing
 * @since 2024-07-07
 */
@Slf4j
@Setter
public abstract class AbstractEasyNetty {

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
     * 关闭方法
     *
     * @return 自身
     */
    public abstract AbstractEasyNetty shutdown();

    /**
     * 线程组关闭方法
     */
    public void shutdownThreadGroup() {
        if (ObjectUtil.isNotNull(this.workGroup)) {
            this.workGroup.shutdownGracefully();
        }
    };

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
    };

    /**
     * 连接是否关闭
     *
     * @return 关闭状态（true：关闭，false：打开）
     */
    public boolean isStop() {
        return !this.isOpen();
    }

}
