package com.catalpawoo.easynetty.starter.annotation;

import com.catalpawoo.easynetty.core.aop.*;
import com.catalpawoo.easynetty.starter.helper.EasyNettyHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 服务端注解初始化处理器
 *
 * @author wuzijing
 * @since 2024-06-23
 */
public class EasyNettyServerAnnotationProcessor implements BeanPostProcessor {

    @Autowired
    private EasyNettyHelper easyNettyHelper;

    @Override
    public Object postProcessAfterInitialization(Object bean, @Nullable String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EnServer.class)) {
            EnServer enServer = bean.getClass().getAnnotation(EnServer.class);
            Method readMethod = null;
            Method addMethod = null;
            Method triggerMethod = null;
            Method removeMethod = null;
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EnRead.class)) {
                    readMethod = method;
                } else if (method.isAnnotationPresent(EnAdd.class)) {
                    addMethod = method;
                } else if (method.isAnnotationPresent(EnEventTrigger.class)) {
                    triggerMethod = method;
                } else if (method.isAnnotationPresent(EnRemove.class)) {
                    removeMethod = method;
                }
            }
            this.initializeServer(enServer, bean, readMethod, addMethod, triggerMethod, removeMethod);
        }
        return bean;
    }

    /**
     * 初始化服务端方法
     *
     * @param enServer      服务端注解
     * @param serverDO      服务端实体类
     * @param readMethod    读取方法
     * @param addMethod     新增连接方法
     * @param triggerMethod 事件触发方法
     * @param removeMethod  移除连接方法
     */
    private void initializeServer(EnServer enServer, Object serverDO, Method readMethod, Method addMethod, Method triggerMethod, Method removeMethod) {
        easyNettyHelper.startNettyServer(new EasyNettyServerAnnotationHandler(serverDO, readMethod, addMethod, triggerMethod, removeMethod), enServer.path(), enServer.port(), enServer.bossThreadNum());
    }

}