package com.renan.config;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.servlets.AdminServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationPath("/")
public class MyApp extends ResourceConfig {
	
	@Inject
	public MyApp(ServiceLocator serviceLocator) {
		// Metrics
		MetricRegistry metrics = SharedMetricRegistries.setDefault("base-api");
		
		// JVM
		metrics.registerAll(new MemoryUsageGaugeSet());
		metrics.registerAll(new GarbageCollectorMetricSet());

		//final MetricRegistry metricRegistry = new MetricRegistry();
		log.info("---- Starting MyApp ----");

		packages("com.renan.resources");
		packages("com.codahale.metrics.jersey2");

		register(new InstrumentedResourceMethodApplicationListener (metrics));
		
		register(GuiceFeature.class);
		register(JacksonFeature.class);

		ConsoleReporter.forRegistry(metrics)
	        .convertRatesTo(TimeUnit.SECONDS)
	        .convertDurationsTo(TimeUnit.MILLISECONDS)
	        .build()
	        .start(1, TimeUnit.MINUTES);
	}

}