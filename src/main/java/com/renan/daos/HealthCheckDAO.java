package com.renan.daos;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface HealthCheckDAO {
	
	@SqlQuery("select 1")
	boolean ping();
	
}
