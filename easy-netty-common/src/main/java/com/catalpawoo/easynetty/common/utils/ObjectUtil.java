package com.catalpawoo.easynetty.common.utils;

/**
 * 对象工具类
 *
 * @author wuzijing
 * @apiNote 对象工具类
 * @since 2024-06-18
 */
public class ObjectUtil {

    /**
     * 是否为空
     *
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 是否不为空
     *
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

}
