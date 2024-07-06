package com.catalpawoo.easynetty.core.properties.server;

import lombok.Data;

/**
 * 服务端配置参数类
 *
 * @author wuzijing
 * @since 2024-07-05
 */
@Data
public class EnServerProperty {

    /**
     * HTTP聚合器配置
     */
    private EnHttpAggregatorProperty httpAggregator;

    /**
     * 协议处理器配置
     */
    private EnProtocolHandlerProperty protocolHandler;

    /**
     * 创建器配置
     */
    private EnCreatorProperty creator;

}
