/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableModule;
import org.springframework.cloud.stream.annotation.PerMessage;
import org.springframework.cloud.stream.annotation.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author Dave Syer
 *
 */
@EnableModule(Sink.class)
@EnableConfigurationProperties
public class LogSink {

	@Autowired
	private LogSinkProperties logSinkProperties;

	@Bean
	@PerMessage
	public LogSinkProperties logSinkProperties() {
		return new LogSinkProperties();
	}


	private static Logger logger = LoggerFactory.getLogger(LogSink.class);

	@ServiceActivator(inputChannel=Sink.INPUT)
	public void loggerSink(Object payload) {
		System.out.println(logSinkProperties);
		System.out.println(logSinkProperties.getClass());
		logger.info("Received: " + logSinkProperties.getName() + " " + payload);
	}

}
