package com.renan.resources;

import java.io.BufferedReader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import com.renan.models.Task;

public class TaskResourceUnitTest {
	
	TaskResource resource;
	
	@Before
	public void prepare() {
		resource = mock(TaskResource.class);
	}

	@Test
	public void testGetAll() {
		
		List<Task> l = new ArrayList<>();
		Task t = new Task();
		t.setId(1l);
		t.setName("teste");
		t.setStatus("uuuuu");
		
		l.add(t);
		
		when(resource.getAllTasks()).thenReturn(l);
		
		List<Task> tasks = resource.getAllTasks();
		
		Long expected = 1l;
		
		assertEquals(expected, tasks.get(0).getId());
	}
	
	@Test
	public void testPost() {
		
	}
	
}