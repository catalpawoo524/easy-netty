package com.catalpawoo.easynetty.core.properties.server;

import lombok.Data;

/**
 * HTTP聚合器配置参数类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Data
public class EnHttpAggregatorProperty {

    /**
     * 最大内容长度
     * 将多个HTTP消息部分（如HTTP请求头和内容）聚合成一个完整的FullHttpRequest或FullHttpResponse对象。这样做的目的是简化处理逻辑，使得处理器可以更方便地处理完整的HTTP消息，而不必处理多个消息部分。
     * 其中，1024 * 64 指定了聚合的最大内容长度（即64KB）。如果消息内容超过这个长度，HttpObjectAggregator 将会抛出异常。
     */
    private Integer maxLength = 1024 * 64;

}
