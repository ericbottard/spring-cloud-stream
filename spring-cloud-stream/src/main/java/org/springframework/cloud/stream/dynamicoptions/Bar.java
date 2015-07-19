package org.springframework.cloud.stream.dynamicoptions;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by ebottard on 17/07/15.
 */
public class Bar {

    @Autowired
    private Foo foo;

    @PostConstruct
    public void hello() {
        System.out.println(foo);
        System.out.println(foo.getBar());
    }
}
