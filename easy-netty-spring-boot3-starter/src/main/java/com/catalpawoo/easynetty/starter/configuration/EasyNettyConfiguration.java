package com.catalpawoo.easynetty.starter.configuration;

import com.catalpawoo.easynetty.core.annotation.processor.EasyNettyServerAnnotationProcessor;
import com.catalpawoo.easynetty.core.banner.EasyNettyBanner;
import com.catalpawoo.easynetty.core.helper.EasyNettyHelper;
import com.catalpawoo.easynetty.core.properties.EasyNettyProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置类
 *
 * @author wuzijing
 * @since 2024-06-24
 */
@Configuration
@EnableConfigurationProperties({
        EasyNettyProperty.class
})
public class EasyNettyConfiguration {

    @Bean
    public EasyNettyHelper easyNettyHelper() {
        return new EasyNettyHelper();
    }

    @Bean
    public EasyNettyServerAnnotationProcessor easyNettyServerAnnotationProcessor() {
        return new EasyNettyServerAnnotationProcessor();
    }

    @Bean
    public EasyNettyBanner easyNettyBanner(EasyNettyProperty easyNettyProperty) {
        return new EasyNettyBanner(easyNettyProperty.getBannerSwitch());
    }

}
