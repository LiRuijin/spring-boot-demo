package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.annotation.Log;
import com.demo.domain.User;
import com.demo.service.DemoService;

@RestController
public class DemoRestController {
	
	@Autowired
	private DemoService demoService;
	
	@Log("访问用户信息")
	@GetMapping("/rest/hello")
	public List<User> helloworld() {

		return demoService.getUsers();
	}
}
