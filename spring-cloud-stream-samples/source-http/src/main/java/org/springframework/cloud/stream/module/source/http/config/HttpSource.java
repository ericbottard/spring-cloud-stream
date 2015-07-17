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

package org.springframework.cloud.stream.module.source.http.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableModule;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.http.config.HttpInboundEndpointParser;
import org.springframework.integration.http.inbound.HttpRequestHandlingController;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.messaging.MessageChannel;

/**
 * A module that accepts incoming http connections and emits a message payload from either the http body
 * (for methods like POST) or the http parameters (for methods like GET).
 *
 * @author Eric Bottard
 */
@EnableModule(Source.class)
@EnableConfigurationProperties(HttpSourceOptions.class)
public class HttpSource {

	@Autowired
	private HttpSourceOptions options;

	@Autowired
	private Source source;

	//@Bean
	public HttpRequestHandlingController gateway() {
		HttpRequestHandlingController httpRequestHandlingMessagingGateway = new HttpRequestHandlingController(false);
		httpRequestHandlingMessagingGateway.setRequestChannel(source.output());
		httpRequestHandlingMessagingGateway.setBeanName("/foo");
		return httpRequestHandlingMessagingGateway;
	}

}
