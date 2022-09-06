package com.nc.xhq.auth.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StudentFallBack implements IStudentClient {

	@Override
	public Map<String,String> findByName() {
		// TODO Auto-generated method stub
		return null;
	}


}
