package com.catalpawoo.easynetty.spring.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Spring工具类
 *
 * @author wuzijing
 * @since 2024-06-29
 */
@Getter
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 推送事件
     *
     * @param event 事件
     */
    public static void publishEvent(ApplicationEvent event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }

    /**
     * 推送事件
     *
     * @param event 事件
     */
    public static void publishEvent(Object event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }

}
