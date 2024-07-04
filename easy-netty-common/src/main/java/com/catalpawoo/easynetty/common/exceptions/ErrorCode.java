package com.catalpawoo.easynetty.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 错误码实体类
 *
 * @author wuzijing
 * @since 2024-07-04
 */
@Data
@AllArgsConstructor
public class ErrorCode implements Serializable {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误提示
     */
    private final String msg;

}
