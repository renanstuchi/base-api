//package com.renan.config;
//
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import com.google.inject.servlet.GuiceServletContextListener;
//import com.google.inject.servlet.ServletModule;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class GuiceContextListener extends GuiceServletContextListener{
//	
//	public GuiceContextListener() {
//		super();
//	}
//
//	@Override
//	protected Injector getInjector() {
//		log.debug("Create Injector");
//
//		Injector injector = Guice.createInjector(
//				new NamedPropertiesModule(), new DataAccessModule(), 
//				new ServicesModule(), new ResourcesModule(), 
//				new ServletModule() {
//			
//					@Override
//				    protected void configureServlets() {
//				        // excplictly bind GuiceContainer before binding Jersey resources
//				        // otherwise resource won't be available for GuiceContainer
//				        // when using two-phased injection
//				        bind(GuiceContainer.class);
//		
//				        // bind Jersey resources
//				        PackagesResourceConfig resourceConfig = new PackagesResourceConfig("jersey.resources.package");
//				        for (Class<?> resource : resourceConfig.getClasses()) {
//				            bind(resource);
//				        }
//		
//				        // Serve resources with Jerseys GuiceContainer
//				        serve("/*").with(GuiceContainer.class);
//				    }
//		});
//		
//		log.debug("Injector created");
//		return injector;
//	}
//}
