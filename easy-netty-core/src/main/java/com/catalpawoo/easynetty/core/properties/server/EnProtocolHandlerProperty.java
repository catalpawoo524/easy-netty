package com.catalpawoo.easynetty.core.properties.server;

import lombok.Data;

/**
 * 协议处理器配置参数类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Data
public class EnProtocolHandlerProperty {

    /**
     * 是否允许扩展
     * 如果为true，表示允许使用WebSocket扩展（如压缩等）
     */
    private Boolean allowExtensions = true;

    /**
     * 最大帧大小
     * 单个WebSocket帧的最大负载长度，单位是字节。
     */
    private Integer maxFrameSize = 65536;

    /**
     * 是否允许帧分块
     * 如果为true，表示允许接收帧的掩码位与规范不匹配的情况
     */
    private Boolean allowMaskMismatch = true;

    /**
     * 是否校验UTF-8
     * 如果为true，表示在解析WebSocket帧时检查它们是否以正确的UTF-8序列开头
     */
    private Boolean checkStartsWith = true;

    /**
     * 握手超时时间
     * 这是握手的超时时间，单位是毫秒。
     */
    private Long handshakeTimeoutMillis = 10000L;

}
