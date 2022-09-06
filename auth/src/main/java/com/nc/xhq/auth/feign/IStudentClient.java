package com.nc.xhq.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


@FeignClient(name = "nc-feign-demo",fallback = StudentFallBack.class)
public interface IStudentClient {
    
	@RequestMapping(value="student/findByName", method=RequestMethod.GET)
	public Map<String,String> findByName();

}
