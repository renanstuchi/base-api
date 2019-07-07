package com.renan.config;

import com.google.inject.AbstractModule;
import com.renan.services.TaskService;

public class ServicesModule extends AbstractModule{

    @Override
    protected void configure() {
    		bind(TaskService.class);
    }
}
