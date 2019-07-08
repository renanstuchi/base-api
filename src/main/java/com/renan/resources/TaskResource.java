package com.renan.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.renan.models.Task;
import com.renan.services.TaskService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Path("/task")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
	
	@Inject @Setter
	private TaskService service;

	@Path("/{id}")
	@GET
	@Timed(name="getTask(Timed)")
	@Metered(name="getTask(Metered)")
	@ExceptionMetered(name="getTask(Exception)")
	public Task getTask(@PathParam("id") long id) {
		
		log.info("Task GET request started. taskId={}", id);
		
		return service.getById(id);
	}
	
	@GET
	@Timed(name="getAllTasks(Timed)")
	@Metered(name="getAllTasks(Metered)")
	@ExceptionMetered(name="getAllTasks(Exception)")
	public List<Task> getAllTasks() {
		
		log.info("Task GET ALL request started.");

		return service.getAllTasks();
	}
	
	@DELETE
	@Path("/{id}")
	@Timed(name="deleteTask(Timed)")
	@Metered(name="deleteTask(Metered)")
	@ExceptionMetered(name="deleteTask(Exception)")
	public Response deleteTask(@PathParam("id") long id) {
		
		log.info("Task DELETE request started. taskId={}", id);
		
		service.deleteTask(id);
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("/{id}")
	@Timed(name="updateTask(Timed)")
	@Metered(name="updateTask(Metered)")
	@ExceptionMetered(name="updateTask(Exception)")
	public Task updateTask(@PathParam("id") long id, @Valid Task task) {
		
		task.setId(id);
		
		log.info("Task PUT request started. taskId={}", id);
		
		service.updateTask(task);
		
		return service.getById(id);
	}
	
	@POST
	@Timed(name="createTask(Timed)")
	@Metered(name="createTask(Metered)")
	@ExceptionMetered(name="createTask(Exception")
	public Task createTask(@Valid Task task) {
		
		log.info("Task POST request started. task={}", task);
		
		long id = service.createTask(task);
		
		return service.getById(id);
	}
}
