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
import org.hamcrest.CoreMatchers;
//import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import com.renan.constants.TaskStatus;
import com.renan.daos.TaskDAO;
import com.renan.models.Task;
import com.renan.services.TaskService;

public class TaskResourceUnitTest {
	
	TaskResource resource;
	TaskService service;
	TaskDAO dao;
	
	@Before
	public void prepare() {
		
		dao = mock(TaskDAO.class);
		service = spy(new TaskService(dao));
		resource = spy(new TaskResource());
		resource.setService(service);
	}

	@Test
	public void testGetAll() {
		
		Long expected = 1l;
		List<Task> l = new ArrayList<>();
		Task t = new Task();
		t.setId(expected);
		t.setName("teste");
		t.setStatus(TaskStatus.IN_PROGRESS);
		
		l.add(t);
		
		//when(resource.getAllTasks()).thenReturn(l);
		when(dao.getTasks()).thenReturn(l);
		
		List<Task> tasks = resource.getAllTasks();

		assertEquals(expected, tasks.get(0).getId());
		verify(dao).getTasks();
	}
	
	@Test
	public void testPost() {
		
		Task task = new Task();
		task.setName("test");
		task.setDescription("desc");
		task.setAssignedTo("me");
		task.setStatus(TaskStatus.COMPLETED);
		
		doReturn(1l).when(dao).inserTask(task);
		doReturn(task).when(dao).getTaskById(1l);
		
		Task inserted = resource.createTask(task);

		assertEquals(inserted.getName(), task.getName());
		verify(dao).inserTask(task);
	}
	
	@Test
	public void testPostMissingDescription() {
		
		Task task = new Task();
		task.setName("test");
		task.setAssignedTo("me");
		task.setStatus(TaskStatus.COMPLETED);
		
		try {
			resource.createTask(task);
			fail("Should not success");
		} catch(Exception e) {
			assertThat(e.getMessage(), CoreMatchers.containsString("property (description)"));
		}

		verify(dao, never()).inserTask(task);
	}
	
}