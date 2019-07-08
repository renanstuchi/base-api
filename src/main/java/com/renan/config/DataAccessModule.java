package com.renan.config;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.renan.daos.HealthCheckDAO;
import com.renan.daos.TaskDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataAccessModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(Jdbi.class).toProvider(DBIProvider.class).in(Scopes.SINGLETON);
        
        bindDAO(TaskDAO.class);
        bindDAO(HealthCheckDAO.class);
    }
    
	private <T> void bindDAO(Class<T> type) {
		bind(type).toProvider(new DAOProvider<T>(type)).in(Scopes.SINGLETON);
	}
	
	class DAOProvider<T> implements Provider<T> {

		@Inject
		private Jdbi dbi;
		private Class<T> type;
		
		public DAOProvider(Class<T> type) {
			this.type = type;
		}
		
		@Override
		public T get() {
			log.info("DBI.onDemand - {}", this.type);

			dbi.installPlugin(new SqlObjectPlugin());
			
			return dbi.onDemand(this.type);
		}
		
	}
}
