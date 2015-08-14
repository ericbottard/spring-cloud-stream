/*
 * Copyright 2015 the original author or authors.
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

package org.springframework.cloud.stream.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * Created by ericbottard on 14/08/15.
 */
public class ConfigurationPropertiesCustomizer {

	private final ConfigurationPropertiesBindingPostProcessor processor;

	private ThreadLocal<Message<?>> messages = new ThreadLocal<>();

	@Autowired
	private Environment environment;

	private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

	private Map<String, SpelExpression> expressionPropertyNames = new HashMap<>();

	public ConfigurationPropertiesCustomizer(ConfigurationPropertiesBindingPostProcessor processor) {
		this.processor = processor;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		for (PropertySource ps : ((ConfigurableEnvironment) environment).getPropertySources()) {
			if (ps instanceof EnumerablePropertySource) {
				EnumerablePropertySource enumerablePropertySource = (EnumerablePropertySource) ps;
				for (String propName : enumerablePropertySource.getPropertyNames()) {
					if (propName.endsWith("Expression")) {
						String spel = (String) enumerablePropertySource.getProperty(propName);
						expressionPropertyNames.put(propName, spelExpressionParser.parseRaw(spel));
					}
				}
			}
		}

		MutablePropertySources propertySources = new MutablePropertySources();
		propertySources.addFirst(new PropertySource<Object>("dynamicPS") {
			@Override
			public Object getProperty(String name) {
				if (expressionPropertyNames.keySet().contains(name + "Expression")) {
					SpelExpression spelExpression = expressionPropertyNames.get(name + "Expression");
					Message<?> message = messages.get();
					if (message == null) {
						return null;
					}
					Object value = spelExpression.getValue(message);
					return value;
				}
				return null;
			}
		});
		processor.setPropertySources(propertySources);
	}

	public ChannelInterceptor newChannelInterceptor() {
		return new ChannelInterceptorAdapter() {
			@Override
			public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
				System.out.println("Seing " + message + " on " + channel);
				messages.set(message);
				super.postSend(message, channel, sent);
			}
		};
	}
}
