package com.wuhen.shard;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardFactory {
	private static Logger logger = LoggerFactory.getLogger(ShardFactory.class);

	private static Map<String, BaseShard> shardUtilMap;

	static {
		// 初始化工厂map，构造单例的路由工具集合
		shardUtilMap = new HashMap<>();
		for (ShardTableEnum shardTableEnum : ShardTableEnum.values()) {
			try {
				Class shardClass = Class.forName(shardTableEnum.getClassName());
				shardUtilMap.put(shardTableEnum.getTableName(), (BaseShard) shardClass.newInstance());
			} catch (Exception e) {
				logger.error("初始化shard工具异常。tableName={}, class={}", shardTableEnum.getTableName(),shardTableEnum.getClassName());
			}
		}
	}

	public static BaseShard getShard(String tableName) {
		return shardUtilMap.get(tableName);
	}
}
