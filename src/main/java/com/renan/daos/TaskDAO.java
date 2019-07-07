package com.renan.daos;

import java.util.List;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import com.renan.models.Task;

public interface TaskDAO extends Transactional<TaskDAO>, SqlObject{

	@SqlQuery("select * from task")
	@RegisterBeanMapper(Task.class)
	public List<Task> getTasks();
}
