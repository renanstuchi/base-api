package com.renan.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.renan.models.Task;

public class TaskResourceIntegrationTest extends JerseyTest {

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(TaskResource.class);
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
