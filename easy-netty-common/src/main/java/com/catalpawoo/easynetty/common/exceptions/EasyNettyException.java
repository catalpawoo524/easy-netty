package com.catalpawoo.easynetty.common.exceptions;

import lombok.Getter;

/**
 * 服务异常类
 *
 * @author wuzijing
 * @since 2024-06-22
 */
@Getter
public class EasyNettyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int status;

    /**
     * 数据
     */
    private Object data;

    public EasyNettyException(String message) {
        this(message, 400);
    }

    public EasyNettyException(String message, int status) {
        super(message);
        this.status = status;
    }

    public EasyNettyException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public EasyNettyException(String message, Throwable cause, Object data) {
        super(message, cause);
        this.status = 400;
        this.data = data;
    }

    public EasyNettyException(String message, Throwable cause, int status, Object data) {
        super(message, cause);
        this.status = status;
        this.data = data;
    }

    public EasyNettyException(Throwable throwable) {
        super(throwable);
    }

    public EasyNettyException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
