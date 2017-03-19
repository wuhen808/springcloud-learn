package com.wuhen.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;
import com.wuhen.util.IdWorker;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatasourceConfig {

	@Autowired
	DataSourceProperties properties;

	@Autowired
	private MetricRegistry metricRegistry;

	@Bean
	public DataSource dataSource() throws SQLException {
		HikariDataSource ds = (HikariDataSource) DataSourceBuilder.create(properties.getClassLoader())
				.type(properties.getType()).driverClassName(properties.determineDriverClassName())
				.url(properties.determineUrl()).username(properties.determineUsername())
				.password(properties.determinePassword()).build();
		ds.setMetricRegistry(metricRegistry);
		return ds;
	}

	@Bean
	public IdWorker id(){
		return new IdWorker(31, 31);
	}
}
