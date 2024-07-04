package com.catalpawoo.easynetty.spring.configuration;

import com.catalpawoo.easynetty.spring.banner.EasyNettyBanner;
import com.catalpawoo.easynetty.spring.utils.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring项目配置类
 *
 * @author wuzijing
 * @since 2024-06-29
 */
@Configuration
public class EasyNettySpringConfiguration {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    public EasyNettyBanner easyNettyBanner() {
        return new EasyNettyBanner();
    }

}
