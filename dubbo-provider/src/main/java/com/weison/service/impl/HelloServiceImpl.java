package com.weison.service.impl;

import com.weison.api.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHelloName(String name) {
		return "Hello:" + name + "!";
	}

}
