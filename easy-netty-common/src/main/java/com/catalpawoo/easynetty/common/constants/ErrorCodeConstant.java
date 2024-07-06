package com.catalpawoo.easynetty.common.constants;

import com.catalpawoo.easynetty.common.exceptions.ErrorCode;

/**
 * 错误码常量类
 *
 * @author wuzijing
 * @since 2024-07-04
 */
public interface ErrorCodeConstant {

    // ============================ 基础工具相关 1_001_000_001 ============================

    /**
     * 实体类工厂加载失败
     */
    ErrorCode COMMON_BEAN_FACTORY_LOAD_FAILED = new ErrorCode(1_001_000_001, "no ConfigurableListableBeanFactory or ApplicationContext injected.");

    // ============================ 服务端启动相关 1_001_001_001 ============================

    /**
     * 参数缺失
     */
    ErrorCode SERVER_STARTUP_MISSING_PARAMETERS = new ErrorCode(1_001_001_001, "server fail to start, the input parameters are missing.");

    /**
     * 端口绑定异常
     */
    ErrorCode SERVER_STARTUP_PORT_BIND_EXCEPTION = new ErrorCode(1_001_001_002, "server startup failed, port number binding exception.");

    /**
     * 端口绑定失败
     */
    ErrorCode SERVER_STARTUP_PORT_BIND_FAILED = new ErrorCode(1_001_001_003, "server startup failed, port number binding failed.");

}
