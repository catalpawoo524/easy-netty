package com.catalpawoo.easynetty.core.events;

import com.catalpawoo.easynetty.core.creator.client.AbstractEasyNettyClient;
import com.catalpawoo.easynetty.core.creator.server.AbstractEasyNettyServer;
import org.springframework.context.ApplicationEvent;

/**
 * 客户端创建事件
 *
 * @author wuzijing
 * @since 2024-07-07
 */
public class EnClientCreateEvent extends ApplicationEvent {

    public EnClientCreateEvent(AbstractEasyNettyClient creator) {
        super(creator);
    }

}
