package com.renan.config;

import com.google.inject.AbstractModule;
import com.renan.resources.TaskResource;

public class ResourcesModule extends AbstractModule{

	@Override
	public void configure() {
		
		bind(TaskResource.class);
	}
}
