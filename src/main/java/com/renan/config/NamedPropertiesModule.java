package com.renan.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NamedPropertiesModule extends AbstractModule{

	@Override
	protected void configure() {
		String userHome = System.getProperty("user.home");
		
		Properties properties = new Properties();
		boolean result = load(properties);
		
		if(!result) {
			properties.put("db.url", "jdbc:mysql://127.0.0.1:3306/base_api_dev");
			properties.put("db.user", "root");
			properties.put("db.pass", "root");
		}

		log.info("Binding Named Params");
    		Names.bindProperties(binder(), properties);
		
		bind(Properties.class).annotatedWith(Names.named("config")).toInstance(properties);
	}
	
	private boolean load(Properties properties) {
		// Load a properties file if present
		//File cfg = new File(path, file);
		log.info("Looking for config file api.properties");

	    try {
	       // properties.load(new FileReader(cfg));
		    	InputStream is = getClass().getClassLoader().getResourceAsStream("api.properties");
		    	if(is != null && is.available() > 0) {
		        properties.load(is);
		        return true;
		    	}
	    } catch (IOException e) {
	    		throw new IllegalStateException("Could not load file ", e);
	    }

		log.warn("Could not find api.properties");
		return false;
	}

}
