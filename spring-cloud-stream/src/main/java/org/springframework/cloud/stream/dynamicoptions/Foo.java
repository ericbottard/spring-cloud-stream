package org.springframework.cloud.stream.dynamicoptions;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ebottard on 17/07/15.
 */
//@ConfigurationProperties(prefix = "module")
public class Foo {

    private String bar = "default";

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
