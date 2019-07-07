package com.renan.services;

import java.util.List;

import com.google.inject.Inject;
import com.renan.daos.TaskDAO;
import com.renan.models.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskService {

	@Inject
	private TaskDAO dao;
	
	public List<Task> getAllTasks() {
		return dao.getTasks();
	}
}
