//package com.renan.config;
//
//import org.glassfish.hk2.api.Factory;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//
//import com.google.inject.Injector;
//import com.renan.services.TaskService;
//
//public class HK2BridgeModule extends AbstractBinder {
//
//	  private final Injector guiceInjector;
//
//	  public HK2BridgeModule(Injector guiceInjector) {
//	    this.guiceInjector = guiceInjector;
//	  }
//
//	  @Override
//	  protected void configure() {
//
//	    //Here are only the services needed by the resources, they are pulled from guice injector using the factory
//	    bindFactory(new ServiceFactory<TaskService>(guiceInjector, TaskService.class)).to(TaskService.class);
//	    //...all your services here
//	  }
//
//	  //this will allow HK2 to pull dependencies from guice injector
//	  private static class ServiceFactory<T> implements Factory<T> {
//
//	    private final Injector guiceInjector;
//
//	    private final Class<T> serviceClass;
//
//	    public ServiceFactory(Injector guiceInjector, Class<T> serviceClass) {
//
//	      this.guiceInjector = guiceInjector;
//	      this.serviceClass = serviceClass;
//	    }
//
//	    @Override
//	    public T provide() {
//	      return guiceInjector.getInstance(serviceClass);
//	    }
//
//	    @Override
//	    public void dispose(T versionResource) {
//	    }
//	  }
//	}