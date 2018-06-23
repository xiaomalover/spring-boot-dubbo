package com.weison.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.weison.api.HelloService;

@RestController
@RequestMapping("/index")
public class HelloController {

	@Autowired
	private HelloService helloService;

	@GetMapping(value = "/{name}")
	public String printName(@PathVariable("name") String name) {
		return helloService.sayHelloName(name);
	}
}
