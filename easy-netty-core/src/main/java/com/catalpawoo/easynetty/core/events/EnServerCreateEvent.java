package com.catalpawoo.easynetty.core.events;

import com.catalpawoo.easynetty.core.EasyNettyServerCreator;
import org.springframework.context.ApplicationEvent;

/**
 * 服务端创建事件
 *
 * @author wuzijing
 * @since 2024-06-29
 */
public class EnServerCreateEvent extends ApplicationEvent {

    public EnServerCreateEvent(EasyNettyServerCreator creator) {
        super(creator);
    }

}
