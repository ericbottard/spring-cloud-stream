package org.springframework.cloud.stream.module.source.http.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.cloud.stream.module.source.http.config.HttpSource;

@SpringBootApplication
@ComponentScan(basePackageClasses= HttpSource.class)
public class ModuleApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ModuleApplication.class, args);
	}

}
