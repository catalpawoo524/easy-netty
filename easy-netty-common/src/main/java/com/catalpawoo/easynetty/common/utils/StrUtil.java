package com.catalpawoo.easynetty.common.utils;

import com.catalpawoo.easynetty.common.constants.IntegerConstant;

/**
 * 字符串工具类
 *
 * @author wuzijing
 * @since 2024-06-24
 */
public class StrUtil {

    /**
     * 判断字符串是否为空，包括null、空字符串、"null"、"undefined"四种状态
     *
     * @param str 字符串
     * @return 字符串是否为空（true：空，false：非空）
     */
    public static boolean isNullOrUndefined(CharSequence str) {
        if (null == str) {
            return true;
        }
        String strString = str.toString().trim();
        if (IntegerConstant.ZERO == strString.length()) {
            return true;
        }
        return "null".equals(strString) || "undefined".equals(strString);
    }

    /**
     * 判断字符串是否为非空，包括null、空字符串、"null"、"undefined"四种状态
     *
     * @param str 字符串
     * @return 字符串是否为非空（true：非空，false：空）
     */
    public static boolean isNotNullOrUndefined(CharSequence str) {
        return !isNullOrUndefined(str);
    }

}
