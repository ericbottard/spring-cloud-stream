package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.config.PerMessagePropertiesBindingConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import sink.LogSink;

@SpringBootApplication
@ComponentScan(basePackageClasses=LogSink.class)
@Import(PerMessagePropertiesBindingConfiguration.class)
public class SinkApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SinkApplication.class, args);
	}

}
