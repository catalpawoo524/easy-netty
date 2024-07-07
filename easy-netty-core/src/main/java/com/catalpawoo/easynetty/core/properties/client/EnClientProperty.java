package com.catalpawoo.easynetty.core.properties.client;

import lombok.Data;

/**
 * 服务端配置参数类
 *
 * @author wuzijing
 * @since 2024-07-05
 */
@Data
public class EnClientProperty {

    /**
     * HTTP聚合器配置
     */
    private EnHttpAggregatorProperty httpAggregator;

    /**
     * 创建器配置
     */
    private EnCreatorProperty creator;

}
