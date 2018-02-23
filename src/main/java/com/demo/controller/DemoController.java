package com.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.service.DemoService;

@Controller
public class DemoController {

	

	@GetMapping("/fm")
	public String info(Map<String, Object> map) {
		map.put("user", "李瑞金");
		map.put("age", "27");
		return "/demo/index";
	}
}
