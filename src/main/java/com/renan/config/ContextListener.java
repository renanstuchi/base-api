package com.renan.config;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ContextListener extends GuiceServletContextListener {
	public static final String METRICS_REGISTRY_NAME = "base-api";
	
	private ServletContext servletContext;
	
	public static Injector injector;

	public ContextListener() {
		super();

		Logger.getGlobal().setLevel(Level.INFO);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.debug("Init");
		servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	    ServletContext servletContext = servletContextEvent.getServletContext();
	    injector = (Injector) servletContext.getAttribute(Injector.class.getName());
	    
		super.contextDestroyed(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		log.debug("Create Injector");

		injector = Guice.createInjector(new NamedPropertiesModule(), new DataAccessModule(), new ResourcesModule(), 
			new ServletModule() {
				@Override
				protected void configureServlets() {

					String templateDir = servletContext.getRealPath("/WEB-INF/templates");
					bindConstant().annotatedWith(Names.named("templateDir")).to(templateDir);
					
					// Metrics
					MetricRegistry metrics = SharedMetricRegistries.setDefault(METRICS_REGISTRY_NAME);
					
					// JVM
					metrics.registerAll(new MemoryUsageGaugeSet());
					metrics.registerAll(new GarbageCollectorMetricSet());
					
					bind(AdminServlet.class).in(Scopes.SINGLETON);
					serve("/metrics/*").with(AdminServlet.class);
				}
			}
		);

		// Tell jackson about joda time and output dates as ISO strings
		ObjectMapper mapper = injector.getInstance(ObjectMapper.class);
		mapper.registerModule(new JodaModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// Register HealthChecks
		log.debug("Registier health check");
		HealthCheckRegistry healthChecks = new HealthCheckRegistry();
		healthChecks.register("database", injector.getInstance(DatabaseHealthCheck.class));
		
		// we need to add to servlet HealthServlet and Metrics
		// another way would be defining a Listener for each of them like explained in their docs
		MetricRegistry metrics = SharedMetricRegistries.getDefault();
		servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metrics);
		servletContext.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, healthChecks);
		
		FilterRegistration.Dynamic filter = servletContext.addFilter("guiceFilter", GuiceFilter.class);
		filter.addMappingForUrlPatterns(null, true, "/*");
		filter.setAsyncSupported(true);
		
		// Metrics console reporter each 30 min
		ConsoleReporter.forRegistry(metrics)
        		.convertRatesTo(TimeUnit.SECONDS)
        		.convertDurationsTo(TimeUnit.MILLISECONDS)
        		.build()
        		.start(30, TimeUnit.MINUTES);

		log.debug("Injector created");
		return injector;
	}
}