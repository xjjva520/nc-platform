package com.nc.ncfeigndemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

	

	@RequestMapping(value="findByName", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> aa() {
		System.out.println("0000000000000000000000000000");
		Map<String,String> map = new HashMap<>();
		map.put("pwd","{nc}bef89eae08dc6da614f08d6da6ed7c23a96f620c");
		map.put("userName","xujianjian");
		return map;
	}

}
