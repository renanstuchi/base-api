package com.renan.config;

import java.time.temporal.ChronoUnit;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBIProvider implements Provider<Jdbi>{
	
	MysqlDataSource datasource = new MysqlDataSource();
	
	@Inject
	public DBIProvider(DBConfig config) {
		log.info("Config database variables");
		if ( config != null && config.url != null ) {

			datasource.setUrl(config.url);
			datasource.setUser(config.user);
			datasource.setPassword(config.pass);
			
		} else {
			log.info("Running on localhost");
			// get the connection info
			String dbname = System.getenv("LOCAL_DBNAME");
			String user = System.getenv("LOCAL_DBUSER");
			String pass = System.getenv("LOCAL_DBPASS");
			if ( dbname == null ) 
				dbname = "base_api_dev";

			datasource.setUrl("jdbc:mysql://localhost:3306/" + dbname);
			datasource.setUser(user);
			datasource.setPassword(pass);
		}

		log.info("Datasource initialised: {}", datasource.getUrl());
	}

	public Jdbi get() {
		// get the config
		//Jdbi dbi = new Jdbi(datasource);
		Jdbi dbi = Jdbi.create(datasource);
		
		SqlLogger sqlLog = new SqlLogger() {
			@Override
			public void logAfterExecution(StatementContext context) {
				log.info("sql={} binding={} time={}", context.getRenderedSql(), context.getBinding().toString(), context.getElapsedTime(ChronoUnit.MILLIS));
			}
		};

		dbi.setSqlLogger(sqlLog);
		
//		dbi.registerArgumentFactory(new JodaLocalDateTimeArgumentFactory());
//		dbi.registerArgumentFactory(new JodaLocalTimeArgumentFactory());
//		dbi.registerArgumentFactory(new JodaLocalDateArgumentFactory());

		log.info("get DBI");
		return dbi;
	}
	
	static class DBConfig {
		@Inject(optional=true) @Named("db.url") String url;
		@Inject(optional=true) @Named("db.user") String user;
		@Inject(optional=true) @Named("db.pass") String pass;
	}
}
