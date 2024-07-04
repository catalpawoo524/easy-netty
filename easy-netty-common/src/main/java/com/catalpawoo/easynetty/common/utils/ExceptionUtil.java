package com.catalpawoo.easynetty.common.utils;

import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import com.catalpawoo.easynetty.common.exceptions.EasyNettyException;
import com.catalpawoo.easynetty.common.exceptions.ErrorCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常工具类
 *
 * @author wuzijing
 * @since 2024-07-04
 */
@Slf4j
public class ExceptionUtil {

    /**
     * 品牌信息
     */
    private static final String BRAND_INFO = "[Easy-Netty] ";

    /**
     * 创建异常
     *
     * @param errorCode 错误码实体类
     * @return 异常
     */
    public static EasyNettyException create(@NonNull ErrorCode errorCode) {
        outputLogMsg(errorCode);
        return new EasyNettyException(errorCode.getMsg(), errorCode.getCode());
    }

    /**
     * 创建异常
     *
     * @param errorCode 错误码实体类
     * @param objects 参数列表
     * @return 异常
     */
    public static EasyNettyException create(@NonNull ErrorCode errorCode, Object ...objects) {
        outputLogMsg(errorCode, objects);
        return new EasyNettyException(errorCode.getMsg(), errorCode.getCode());
    }

    /**
     * 输出日志消息
     *
     * @param errorCode 错误码
     */
    private static void outputLogMsg(@NonNull ErrorCode errorCode) {
        log.error(BRAND_INFO + errorCode.getMsg());
    }

    /**
     * 输出日志消息
     *
     * @param errorCode 错误码
     * @param objects 参数列表
     */
    private static void outputLogMsg(@NonNull ErrorCode errorCode, Object ...objects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BRAND_INFO);
        stringBuilder.append(errorCode.getMsg());
        if (CollUtil.isNotEmpty(objects)) {
            stringBuilder.append(" parameters: ");
            int index = IntegerConstant.ZERO;
            for (Object obj : objects) {
                stringBuilder.append(obj);
                if (objects.length != index) {
                    stringBuilder.append(", ");
                } else {
                    stringBuilder.append(".");
                }
                index ++;
            }
        }
        log.error(stringBuilder.toString());
    }

}
