package com.renan.services;

import java.util.List;

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
		return dao.getTasks();
	}
}
