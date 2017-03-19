package com.wuhen.shard;

import java.util.List;

import org.apache.ibatis.mapping.ParameterMapping;

import com.wuhen.model.TestModel;

public class TestShard implements BaseShard {
	
	private static final String TABLE_NAME="T_TEST";

	@Override
	public String getTableName(List<ParameterMapping> paramsMapping, Object param) {
		for (ParameterMapping par : paramsMapping) {
			//获取参数名par.getProperty()
			if(par.getProperty().equalsIgnoreCase("vipId")){
				TestModel t=(TestModel)param;
				return String.format("%s_%s", TABLE_NAME,(t.getVipId()%5));
			}
		}
		return TABLE_NAME;
	}

}
