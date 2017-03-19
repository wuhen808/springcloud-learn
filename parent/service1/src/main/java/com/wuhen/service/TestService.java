package com.wuhen.service;

import com.wuhen.model.TestModel;

public interface TestService {

	TestModel queryByVip(TestModel testModel);

	void insert(TestModel model);

}