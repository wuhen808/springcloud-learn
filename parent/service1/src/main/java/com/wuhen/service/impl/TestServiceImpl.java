package com.wuhen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wuhen.mapper.TestMapper;
import com.wuhen.model.TestModel;
import com.wuhen.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper mapper;

	
	@Override
	public TestModel queryByVip(TestModel testModel) {
		return mapper.queryByVip(testModel);
	}

	
	@Override
	public void insert(TestModel model){
		mapper.insert(model);
	}
}
