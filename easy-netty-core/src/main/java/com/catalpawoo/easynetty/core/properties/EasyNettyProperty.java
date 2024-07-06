package com.catalpawoo.easynetty.core.properties;

import com.catalpawoo.easynetty.core.properties.server.EnServerProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总配置参数类
 *
 * @author wuzijing
 * @since 2024-07-05
 */
@Data
@ConfigurationProperties(prefix = "easy-netty")
public class EasyNettyProperty {

    /**
     * 是否开启横幅
     */
    private Boolean bannerSwitch = true;

    /**
     * 服务端配置
     */
    private EnServerProperty server;

}
