package com.renan.resources;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.google.inject.servlet.GuiceFilter;
import com.renan.config.GuiceFeature;
import com.renan.config.MyApp;
import com.renan.models.Task;

public class TaskResourceIT extends JerseyTest {
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig().packages("com.renan.resources").register(GuiceFeature.class);
	}
	
	@Test
	public void testGetAll() {
		Long idExpted = 2l;
		
		Response response = target("/task").request().get();
		List<Task> tasks = response.readEntity(new GenericType<List<Task>>(){});
		
		assertEquals("Expected Status = 200", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Expected Id = 2", idExpted, tasks.get(0).getId());
	}
	
	@Test
	public void testGetUniqueById() {
		
		Long idSearched = 1l;
		
		Response response = target("/task/" + idSearched).request().get();
		Task task = response.readEntity(Task.class);
		
		assertEquals("Expected Status = 200", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Expected Id = 1", idSearched, task.getId());
	}
}
