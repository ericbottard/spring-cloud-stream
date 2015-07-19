package org.springframework.cloud.stream.dynamicoptions;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.config.GlobalChannelInterceptorInitializer;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * Created by ebottard on 18/07/15.
 */
@Configuration
public class MyConfiguration {

    private final MessageScope scope = new MessageScope();

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("message", scope);
        return configurer;
    }

    @Bean
    @GlobalChannelInterceptor
    public ChannelInterceptor interceptor() {
        return scope.channelInterceptor();
    }

    @Bean
    public GlobalChannelInterceptorInitializer globalChannelInterceptorInitializer() {
        return new GlobalChannelInterceptorInitializer();
    }

}
