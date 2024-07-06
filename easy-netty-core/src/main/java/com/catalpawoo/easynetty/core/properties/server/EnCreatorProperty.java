package com.catalpawoo.easynetty.core.properties.server;

import com.catalpawoo.easynetty.common.constants.IntegerConstant;
import lombok.Data;

/**
 * 创建器配置参数类
 *
 * @author wuzijing
 * @since 2024-07-06
 */
@Data
public class EnCreatorProperty {

    /**
     * 连接请求等待队列大小
     */
    private Integer soBacklog = IntegerConstant.ONE_THOUSAND_TWENTY_FOUR;

    /**
     * 是否开启快速复用
     * 可以在服务器重启后立即重新绑定到相同的端口，防止端口被占用
     */
    private Boolean soReuseaddr = true;

    /**
     * 是否禁用Nagle算法
     * 禁用Nagle算法，以减少延迟
     */
    private Boolean tcpNoDelay = true;

}
