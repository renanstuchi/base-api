package com.renan.config;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Provider
public class GuiceFeature implements Feature {

    private final ServiceLocator sl;

    @Inject
    public GuiceFeature(final ServiceLocator sl) {
        this.sl = sl;
    }

	@Override
    public boolean configure(FeatureContext context) {
       // ServiceLocator serviceLocator = InjectionManager.getInstance(ServiceLocator.class);
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(sl);
        GuiceIntoHK2Bridge guiceBridge = sl.getService(GuiceIntoHK2Bridge.class);
        
        Injector injector = Guice.createInjector(new NamedPropertiesModule(), new DataAccessModule(), new ServicesModule(), new ResourcesModule());
		
        guiceBridge.bridgeGuiceInjector(injector);
        
        return true;
    }
}