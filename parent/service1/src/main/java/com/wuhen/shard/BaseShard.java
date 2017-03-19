package com.wuhen.shard;

import java.util.List;

import org.apache.ibatis.mapping.ParameterMapping;

public interface BaseShard {

    public String getTableName(List<ParameterMapping> paramsMapping, Object param);  
}

