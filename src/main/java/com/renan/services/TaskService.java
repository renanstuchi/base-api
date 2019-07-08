package com.renan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.inject.Inject;
import com.renan.daos.TaskDAO;
import com.renan.models.Task;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class TaskService {

	@Inject
	private TaskDAO dao;
	
	public TaskService(TaskDAO dao) {
		this.dao = dao;
	}
	
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		
		try {
			tasks = dao.getTasks();
		} catch(Exception e) {
			log.error("Unexpected Error fetching Tasks. method=getAllTasks.", e);
			throw new RuntimeException("Unexpected Error fetching Tasks. Please, try again.", e);
		}
		
		return tasks;
	}
	
	public Task getById(long id) {
		
		Task task = null;
		
		try {
			task = dao.getTaskById(id);
		} catch(Exception e) {
			log.error("Unexpected Error fetching Task. method=getById. taskId={}", id, e);
			throw new RuntimeException("Unexpected Error fetching Task. Please, try again.", e);
		}
		
		if(task == null) {
			throw new NoSuchElementException("Task not found.");
		}
		
		return task;
	}
	
	public long createTask(Task task) {
		
		checkModelConsistency(task);

		try {
			long id = dao.inserTask(task);
			
			return id;
		} catch(Exception e) {
			log.error("Unexpected Error fetching Task. method=createTask. task={}", task, e);
			throw new RuntimeException("Unexpected Error Inserting Task. Please, try again.", e);
		}
		
	}
	
	public void updateTask(Task task) {
		
		if(task.getId() == null) {
			throw new NoSuchElementException("Task without ID. Cant update.");
		}
		
		checkModelConsistency(task);
		
		int cnt = 0;
		
		try {
			cnt = dao.updateTask(task);
		} catch(Exception e) {
			log.error("Unexpected Error fetching Task. method=updateTask. task={}", task, e);
			throw new RuntimeException("Unexpected Error Updating Task. Please, try again.", e);
		}
		
		if(cnt <= 0) {
			throw new NoSuchElementException("None tasks updated.");
		}
	}
	
	public void deleteTask(long id) {
		int cnt = 0;
		
		try {
			cnt = dao.deleteTask(id);
		} catch(Exception e) {
			log.error("Unexpected Error fetching Task. method=deleteTask. taskId={}", id, e);
			throw new RuntimeException("Unexpected Error Deleting Task. Please, try again.", e);
		}
		
		if(cnt <= 0) {
			throw new NoSuchElementException("None tasks deleted.");
		}
	}
	
	private void checkModelConsistency(Task task) {
		if(task.getName() == null || task.getName().isEmpty()) {
			throw new RuntimeException("Task property (name) is mandatory.");
		}
		
		if(task.getDescription() == null || task.getDescription().isEmpty()) {
			throw new RuntimeException("Task property (description) is mandatory.");
		}
		
		if(task.getAssignedTo() == null || task.getAssignedTo().isEmpty()) {
			throw new RuntimeException("Task property (assignedTo) is mandatory.");
		}
		
		if(task.getStatus() == null) {
			throw new RuntimeException("Task property (status) is mandatory.");
		}
	}
}
