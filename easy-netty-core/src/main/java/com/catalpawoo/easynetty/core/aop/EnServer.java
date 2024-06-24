package com.catalpawoo.easynetty.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务端主注解
 *
 * @author wuzijing
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnServer {

    /**
     * 接口路径
     *
     * @return 接口路径
     */
    String path();

    /**
     * 端口
     *
     * @return 端口
     */
    int port();

    /**
     * 主线程数量
     *
     * @return 主线程数量
     */
    int bossThreadNum() default 1;

}

