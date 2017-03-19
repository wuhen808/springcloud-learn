package com.wuhen.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration  
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {
	@Autowired
	DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
//		// 分页插件
//		PageHelper pageHelper = new PageHelper();
//		Properties props = new Properties();
//		props.setProperty("reasonable", "false");
//		props.setProperty("supportMethodsArguments", "true");
//		props.setProperty("returnPageInfo", "none");
//		props.setProperty("rowBoundsWithCount", "false");
//		props.setProperty("offsetAsPageNum", "true");
//		pageHelper.setProperties(props);
//		// 添加插件
		
		bean.setPlugins(new Interceptor[] { new com.wuhen.interceptor.ShardInterceptor() });
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources("classpath:com/wuhen/mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
