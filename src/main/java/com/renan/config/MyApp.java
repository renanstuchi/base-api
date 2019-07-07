package com.renan.config;

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

import com.google.inject.Guice;
import com.google.inject.Injector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationPath("/")
public class MyApp extends ResourceConfig {
	
	@Inject
	public MyApp(ServiceLocator serviceLocator) {
		
		log.info("---- Starting MyApp ----");

		packages("com.renan.resources");
		
		register(JacksonFeature.class);
		
		Injector injector = Guice.createInjector(new NamedPropertiesModule(), new DataAccessModule(), new ServicesModule(), new ResourcesModule());
		initGuiceIntoHK2Bridge(serviceLocator, injector);
	}
	
	private void initGuiceIntoHK2Bridge(ServiceLocator serviceLocator, Injector injector) {
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(injector);
	}
}