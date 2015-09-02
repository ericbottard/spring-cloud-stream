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

package org.springframework.boot.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import org.springframework.boot.loader.jar.Handler;
import org.springframework.util.Assert;

/**
 * A variation of LaunchedURLClassLoader which <em>does</em> consult its parent when calling
 * {@link #getResources(String)}. This is so that jars that are <i>e.g.</i> on the system ClassLoader
 * (as passed to {@literal java -cp}) are consulted as well.
 */
public class ParentConsultingLaunchedURLClassLoader extends LaunchedURLClassLoader {

	public ParentConsultingLaunchedURLClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, assertURLClassLoader(parent));
	}

	private static ClassLoader assertURLClassLoader(ClassLoader parent) {
		if (parent != null) {
			Assert.isInstanceOf(URLClassLoader.class, parent, "parent ClassLoader has the wrong type");
		}
		return parent;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		// We want to use parent.findResources() (NOT parent.getResources()), which
		// is only visible in URLClassLoader.
		if (getParent() instanceof URLClassLoader) {
			URLClassLoader parent = (URLClassLoader) getParent();
			return new ResourceEnumeration(super.getResources(name), parent.findResources(name));
		} else {
			return super.getResources(name);
		}
	}

	@Override
	public URL getResource(String name) {
		URL resource = super.getResource(name);
		if (resource != null) {
			return resource;
		} else if (getParent() instanceof URLClassLoader) {
			return ((URLClassLoader) getParent()).findResource(name);
		} else {
			return null;
		}
	}

	/**
	 * {@link Enumeration} implementation used for {@code getResources()}.
	 */
	private static class ResourceEnumeration implements Enumeration<URL> {

		private final Enumeration<URL> rootResources;

		private final Enumeration<URL> localResources;

		public ResourceEnumeration(Enumeration<URL> rootResources,
				Enumeration<URL> localResources) {
			this.rootResources = rootResources;
			this.localResources = localResources;
		}

		@Override
		public boolean hasMoreElements() {
			try {
				Handler.setUseFastConnectionExceptions(true);
				return this.rootResources.hasMoreElements()
						|| this.localResources.hasMoreElements();
			}
			finally {
				Handler.setUseFastConnectionExceptions(false);
			}
		}

		@Override
		public URL nextElement() {
			if (this.rootResources.hasMoreElements()) {
				return this.rootResources.nextElement();
			}
			return this.localResources.nextElement();
		}

	}
}
