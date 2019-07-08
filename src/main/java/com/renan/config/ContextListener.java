package com.renan.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ContextListener implements ServletContextListener {
	
	public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        log.info("=== Configuring Constellation ===");

        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

        initMetrics(context, disps);

        log.info( "=== Configuration ended ===");

//        ServletRegistration.Dynamic servlet2 = context.addServlet("MetricsServlet", AdminServlet.class);
//        servlet2.addMapping("/metrics/*");
//        servlet2.setLoadOnStartup(0);
    }
    
    private  void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
    		log.info( "Initializing Metrics registries");
      //  servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, METRIC_REGISTRY);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,  METRIC_REGISTRY);
        servletContext.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, HEALTH_CHECK_REGISTRY);

        log.info("Registering Metrics Filter");
//        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter",
//                new InstrumentedFilter());

//        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
//        metricsFilter.setAsyncSupported(true);
		
        log.info("Registering Metrics Admin Servlet");
        ServletRegistration.Dynamic metricsAdminServlet =
                servletContext.addServlet("metrics", new AdminServlet());

        metricsAdminServlet.addMapping("/metrics/*");
        metricsAdminServlet.setLoadOnStartup(2);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}