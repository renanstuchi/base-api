package com.renan.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.renan.models.Task;
import com.renan.services.TaskService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Path("/task")
@Slf4j
public class TaskResource {
	
	@Inject @Setter
	private TaskService service;

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(name="timeGET")
	@Metered(name="countGET")
	@ExceptionMetered(name="exceptionCountGET")
	public Task getTask(@PathParam("id") Long id) {
		
		Task t = new Task();
		t.setId(1l);
		t.setName("teste");
		t.setStatus("uuuuu");
		
		return t;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(name="timeGETALL")
	@Metered(name="countGETALL")
	@ExceptionMetered(name="exceptionCountGETALL")
	public List<Task> getAllTasks() {
		
		log.info("GET ALL request.");
		
		List<Task> l = new ArrayList<>();
		Task t = new Task();
		t.setId(1l);
		t.setName("teste");
		t.setStatus("uuuuu");
		
		l.add(t);
		
		//return l;
		return service.getAllTasks();
	}
	
	@DELETE
	@Path("/{id}")
	@Timed(name="timeDELETE")
	@Metered(name="countDELETE")
	@ExceptionMetered(name="exceptionCountDELETE")
	public void deleteTask(@PathParam("id") Long id) {
		
		log.info("Deleting task={}", id);
	}
	
	@PUT
	@Path("/{id}")
	@Timed(name="timePUT")
	@Metered(name="countPUT")
	@ExceptionMetered(name="exceptionCountPUT")
	public Task updateTask(@PathParam("id") Long id, Task task) {
		
		task.setId(id);
		
		log.info("Updating task={}", id);
		
		return getTask(id);
	}
	
	@POST
	@Timed(name="timePOST")
	@Metered(name="countPOST")
	@ExceptionMetered(name="exceptionCountPOST")
	public Task createTask(Task task) {
		
		log.info("Creating new task={}", task);
		
		long id = 0;
		
		return getTask(id);
	}
}
