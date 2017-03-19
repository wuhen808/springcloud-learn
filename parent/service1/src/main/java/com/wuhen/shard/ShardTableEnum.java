package com.wuhen.shard;

public enum ShardTableEnum {
	T_TEST("T_TEST", "com.wuhen.shard.TestShard");

	private String tableName;
	private String className;

	private ShardTableEnum(String tableName, String className) {
		this.tableName = tableName;
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public String getTableName() {
		return tableName;
	}

}
