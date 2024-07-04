package com.catalpawoo.easynetty.common.utils;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author wuzijing
 * @since 2024-07-04
 */
public class CollUtil {

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空（True：空，False：非空）
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (ObjectUtil.isNull(collection)) {
            return true;
        }
        return collection.isEmpty();
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空（True：空，False：非空）
     */
    public static boolean isEmpty(Object[] collection) {
        if (ObjectUtil.isNull(collection)) {
            return true;
        }
        return collection.length == 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空（True：空，False：非空）
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return 是否为空（True：空，False：非空）
     */
    public static boolean isNotEmpty(Object[] collection) {
        return !isEmpty(collection);
    }

}
