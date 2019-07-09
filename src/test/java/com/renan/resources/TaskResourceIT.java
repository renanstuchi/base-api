package com.renan.resources;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.inject.servlet.GuiceFilter;
import com.renan.config.GuiceFeatureTests;
import com.renan.config.MyApp;
import com.renan.constants.TaskStatus;
import com.renan.models.Task;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskResourceIT extends JerseyTest {

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig().packages("com.renan.resources").register(GuiceFeatureTests.class);
	}
	
	@Test
	public void testEndpoints() {

		Response response = target("/task").request()
				.post(Entity.json("{\"name\":\"Task1\",\"description\":\"My description\", \"assignedTo\": \"Me\", \"status\": \"OPEN\"}"));
		
		Task task = response.readEntity(Task.class);
		
		assertEquals("Expected Status = OPEN", task.getStatus(), TaskStatus.OPEN);
		
		Response responseGet = target("/task/" + task.getId()).request().get();
		Task internalTask = responseGet.readEntity(Task.class);
		
		assertEquals("Expected Status = 200", Status.OK.getStatusCode(), responseGet.getStatus());
		assertEquals("Expected Name = Task1", internalTask.getName(), task.getName());
		
		Response responseDel = target("/task/" + task.getId()).request().delete();
		
		assertEquals("Expected Status = 204", Status.NO_CONTENT.getStatusCode(), responseDel.getStatus());
	}

}
