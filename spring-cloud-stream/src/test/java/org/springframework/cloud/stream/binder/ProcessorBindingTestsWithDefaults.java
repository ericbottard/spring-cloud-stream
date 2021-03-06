/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.binder;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Properties;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.annotation.EnableModule;
import org.springframework.cloud.stream.annotation.ModuleChannels;
import org.springframework.cloud.stream.annotation.Processor;
import org.springframework.cloud.stream.utils.MockBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Marius Bogoevici
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ProcessorBindingTestsWithDefaults.TestProcessor.class)
public class ProcessorBindingTestsWithDefaults {

	@Autowired
	private Binder binder;

	@Autowired @ModuleChannels(TestProcessor.class)
	private Processor processor;

	@Test
	public void testSourceOutputChannelBound() {
		Mockito.verify(binder).bindConsumer(eq("input"), eq(processor.input()), Mockito.<Properties>any());
		Mockito.verify(binder).bindProducer(eq("output"), eq(processor.output()), Mockito.<Properties>any());
		verifyNoMoreInteractions(binder);
	}

	@EnableModule(Processor.class)
	@EnableAutoConfiguration
	@Import(MockBinderConfiguration.class)
	public static class TestProcessor {

	}
}
