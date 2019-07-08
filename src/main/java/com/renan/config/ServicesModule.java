package com.renan.config;

import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.renan.services.TaskService;

public class ServicesModule extends AbstractModule{

    @Override
    protected void configure() {
    		bind(TaskService.class);
    		
    		bind(AdminServlet.class).in(Scopes.SINGLETON);
    		bind(MetricsServlet.class).in(Scopes.SINGLETON);
    }
}
