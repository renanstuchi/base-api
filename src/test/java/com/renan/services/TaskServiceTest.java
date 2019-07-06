package com.renan.services;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

	TaskService service;
	
	@Before
	public void prepare() {
		service = spy(new TaskService());
	}
	
	@Test
	public void testFetchDB() {
		
	}
}
