package com.wuhen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wuhen.model.TestModel;
import com.wuhen.service.TestService;
import com.wuhen.util.IdWorker;

@RestController
public class TestController {

	@Autowired
	private TestService testService;
	@Autowired
	private IdWorker idWorker;
	
	@RequestMapping("/find/{vipId}")
	public TestModel queryOne(@PathVariable long vipId){
		TestModel testModel=new TestModel();
		testModel.setVipId(vipId);
		return testService.queryByVip(testModel );
	}
	
	@RequestMapping("/add")
	public void add(){
		TestModel testModel=new TestModel();
		testModel.setId("111");
		testModel.setName("222");
		testModel.setVipId(idWorker.nextId());
		testService.insert(testModel);
	}
}
