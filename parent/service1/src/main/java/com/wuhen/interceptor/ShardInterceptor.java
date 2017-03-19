package com.wuhen.interceptor;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import com.wuhen.shard.BaseShard;
import com.wuhen.shard.ShardFactory;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class ShardInterceptor implements Interceptor {

	private static final ObjectFactory objectFactory = new DefaultObjectFactory();
	private static final ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory reflectorFactory = new DefaultReflectorFactory();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, objectFactory, objectWrapperFactory,
				reflectorFactory);
		// 获取sql
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		// 重写sql
		String newSql = getSql(boundSql);
		metaStatementHandler.setValue("delegate.boundSql.sql", newSql);

		// MappedStatement m=(MappedStatement)
		// metaStatementHandler.getValue("delegate.mappedStatement");
		// String id=m.getId();
		// String className=id.substring(0,id.lastIndexOf("."));
		// Class<?> classObj = Class.forName(className);
		// 将执行权交给下一个拦截器
		return invocation.proceed();
	}

	// 获取分表后的sql
	private String getSql(BoundSql boundSql) {
		// 0、获取sql
		String sql = boundSql.getSql().trim().toUpperCase();
		// 1、获取表名
		String tableName = getTableName(sql);
		// 2、获取新sql
		return getShardSql(sql, tableName, boundSql.getParameterMappings(), boundSql.getParameterObject());
	}

	// 构造新sql
	private String getShardSql(String sql, String tableName, List<ParameterMapping> paramsMapping, Object params) {
		// 判断是否需要路由策略
		BaseShard shardUtil = ShardFactory.getShard(tableName);
		if (shardUtil == null) {
			return sql;
		}
		String shardTableName = shardUtil.getTableName(paramsMapping, params);
		return sql.replaceFirst(tableName, shardTableName);
	}

	// 根据sql获取表名
	private String getTableName(String sql) {
		String[] sqls = sql.split("\\s+");
		switch (sqls[0]) {
		case "SELECT": {
			for (int i = 0; i < sqls.length; i++) {
				if (sqls[i].equals("FROM")) {
					return sqls[i + 1];
				}
			}
			break;
		}
		case "UPDATE": {
			return sqls[1];
		}
		case "INSERT": {
			if(sqls[2].contains("(")){
				return sqls[2].substring(0,sqls[2].indexOf("("));
			}
			return sqls[2];
		}
		case "DELETE": {
			return sqls[1];
		}
		}
		return null;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
