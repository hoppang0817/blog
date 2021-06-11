package com.bit.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //특정 언노테이션이 붙어있는 클래스 파일들을 new해서(ioc) 스프링 컨테이너에 관해준다.
public class BlogControllerTest {
	
	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return"<h1>hello Spring boot</h1>";
	}
}
