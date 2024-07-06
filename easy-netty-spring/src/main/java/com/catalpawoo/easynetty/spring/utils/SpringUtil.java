package com.catalpawoo.easynetty.spring.utils;

import com.catalpawoo.easynetty.common.constants.ErrorCodeConstant;
import com.catalpawoo.easynetty.common.utils.ExceptionUtil;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
@Component
public class SpringUtil implements ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * 实体类工厂
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * 上下文
     */
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtil.beanFactory = configurableListableBeanFactory;
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

    /**
     * 获取实体类工厂
     *
     * @return 实体类工厂
     */
    public static ListableBeanFactory getBeanFactory() {
        ListableBeanFactory factory = null == beanFactory ? applicationContext : beanFactory;
        if (ObjectUtil.isNull(factory)) {
            throw ExceptionUtil.create(ErrorCodeConstant.COMMON_BEAN_FACTORY_LOAD_FAILED);
        } else {
            return factory;
        }
    }

    /**
     * 获取Spring管理的实体类
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 实体类
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

}
