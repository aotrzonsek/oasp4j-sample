package io.oasp.gastronomy.restaurant.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.DatabaseMigrator;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfigurator {
	@Inject
	@Qualifier("appDataSource")
	private JdbcDataSource datasource;
	
	@Value(value = "${database.migration.auto}")
	private boolean enabled;
	
	@Value(value = "${database.hibernate.show.sql}")
	private boolean showSql;
	
	@Value(value = "${database.hibernate.dialect}")
	private String databasePlatform;
	
	@Value(value = "${database.hibernate.hbm2ddl.auto}")
	private String auto;
		
	@Bean(name="flyway", initMethod="migrate")
	public DatabaseMigrator databaseMigrator(){
		DatabaseMigrator databaseMigrator = new DatabaseMigrator();
		databaseMigrator.setDataSource(datasource);
		databaseMigrator.setEnabled(enabled);
		
		return databaseMigrator;
	}

	@Bean(name="entityManagerFactory")
	@DependsOn({"flyway"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPackagesToScan("io.oasp.gastronomy.restaurant.*.dataaccess");
		entityManagerFactory.setDataSource(datasource);
		entityManagerFactory.setMappingResources("config/app/dataaccess/NamedQueries.xml");
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(showSql);
		jpaVendorAdapter.setDatabasePlatform(databasePlatform);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		
		Map<String, String> jpaProperties = new HashMap<String,String>();
		jpaProperties.put("hibernate.hbm2ddl.aut", auto);
		entityManagerFactory.setJpaPropertyMap(jpaProperties);
		
		return entityManagerFactory;
	}
		
	@Bean(name="transactionManager")
	@Inject
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		
		return transactionManager;
	}
	
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean(name="entityManagerFactoryBean")
	@Inject
	public org.springframework.orm.jpa.support.SharedEntityManagerBean sharedEntityManagerBean(EntityManagerFactory entityManagerFactory){
		SharedEntityManagerBean sharedEntityManagerBean = new org.springframework.orm.jpa.support.SharedEntityManagerBean();
		sharedEntityManagerBean.setEntityManagerFactory(entityManagerFactory);
		
		return sharedEntityManagerBean;
	}
}
