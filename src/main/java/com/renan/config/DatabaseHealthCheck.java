package com.renan.config;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;
import com.google.inject.Inject;
import com.renan.daos.HealthCheckDAO;

public class DatabaseHealthCheck extends HealthCheck {
	private HealthCheckDAO dao;
	
	@Inject
	public DatabaseHealthCheck(HealthCheckDAO dao) {
		super();
		this.dao = dao;
	}

  @Override
  protected Result check() throws Exception {
      dao.ping();
      return Result.healthy();
  }
}