package com.catalpawoo.easynetty.starter.configuration;

import com.catalpawoo.easynetty.starter.annotation.EasyNettyServerAnnotationProcessor;
import com.catalpawoo.easynetty.starter.helper.EasyNettyHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置类
 *
 * @author wuzijing
 * @since 2024-06-24
 */
@Configuration
public class EasyNettyConfiguration {

    @Bean
    public EasyNettyHelper easyNettyHelper() {
        return new EasyNettyHelper();
    }

    @Bean
    public EasyNettyServerAnnotationProcessor easyNettyServerAnnotationProcessor() {
        return new EasyNettyServerAnnotationProcessor();
    }

}
