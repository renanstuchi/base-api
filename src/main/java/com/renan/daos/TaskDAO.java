package com.renan.daos;

import java.util.List;

import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import com.renan.models.Task;

public interface TaskDAO extends Transactional<TaskDAO>, SqlObject{

	@SqlQuery("select * from task")
	@RegisterBeanMapper(Task.class)
	public List<Task> getTasks();
	
	@SqlQuery("select * from task where id = :id")
	@RegisterBeanMapper(Task.class)
	public Task getTaskById(@Bind("id") long id);
	
	@SqlUpdate("insert into task (name, description, assignedTo, status, created) values (:name, :description, :assignedTo, :statusString, CURRENT_TIMESTAMP)")
	@GetGeneratedKeys
	public Long inserTask(@BindBean Task task);
	
	@SqlUpdate("update task set name = :name, description = :description, assignedTo = :assignedTo, status = :statusString where id = :id")
	public int updateTask(@BindBean Task task);
	
	@SqlUpdate("udapte task set status = :statusString where id = :id")
	public int updateTaskStatus(@Bind("id") long id);
	
	@SqlUpdate("delete from task where id = :id")
	public int deleteTask(@Bind("id") long id);
}
