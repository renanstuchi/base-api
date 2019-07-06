package com.renan.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.renan.resources.TaskResource;

@ApplicationPath("/")
public class MyApp extends ResourceConfig {
	public MyApp() {
		
		//super(TaskResource.class, JacksonFeature.class);
		
		register(TaskResource.class);
		
		register(JacksonFeature.class);
	}
}

//public class App extends Application{
//
//	@Override
//  public Set<Class<?>> getClasses()
//  {
//      final Set<Class<?>> classes = new HashSet<>();
//      
//      // add all my Resources
//      classes.add(TaskResource.class);
//      
//      return classes;
//  }
//}