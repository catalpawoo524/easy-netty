package com.catalpawoo.easynetty.common.builder;

import java.io.Serializable;

/**
 * 构造器接口类
 *
 * @author wuzijing
 * @apiNote 构造器接口类
 * @since 2024-06-22
 */
public interface Builder<T> extends Serializable {

    /**
     * 构造完成方法
     *
     * @return 构造结果
     */
    T build();

}