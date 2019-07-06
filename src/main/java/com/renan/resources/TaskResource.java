package com.renan.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.renan.models.Task;

import lombok.extern.slf4j.Slf4j;

@Path("/task")
@Slf4j
public class TaskResource {

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTask(@PathParam("id") Long id) {
		
		Task t = new Task();
		t.setId(1l);
		t.setName("teste");
		t.setStatus("uuuuu");
		
		return t;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getAllTasks() {
		
		log.info("GET ALL request.");
		
		List<Task> l = new ArrayList<>();
		Task t = new Task();
		t.setId(1l);
		t.setName("teste");
		t.setStatus("uuuuu");
		
		l.add(t);
		
		//return "HELLO WORLD";
		return l;
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteTask(@PathParam("id") Long id) {
		
		log.info("Deleting task={}", id);
	}
	
	@PUT
	@Path("/{id}")
	public Task updateTask(@PathParam("id") Long id, Task task) {
		
		task.setId(id);
		
		log.info("Updating task={}", id);
		
		return getTask(id);
	}
	
	@POST
	public Task createTask(Task task) {
		
		log.info("Creating new task={}", task);
		
		long id = 0;
		
		return getTask(id);
	}
}
