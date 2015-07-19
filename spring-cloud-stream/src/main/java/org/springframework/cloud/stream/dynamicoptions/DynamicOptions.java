package org.springframework.cloud.stream.dynamicoptions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope(value= "message", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ConfigurationProperties(prefix = "module", locations = "magical:bar")
public @interface DynamicOptions {
}
