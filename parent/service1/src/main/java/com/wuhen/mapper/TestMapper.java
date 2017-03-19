package com.wuhen.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.wuhen.model.TestModel;

@Mapper
public interface TestMapper {

	TestModel queryByVip(TestModel model);

	void insert(TestModel model);
}
