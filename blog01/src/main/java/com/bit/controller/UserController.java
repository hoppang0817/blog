package com.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할 수있는 경로를 /auth/허용
// 그냥주소가 / 이면 index1.jsp허용
//static 이하에 있는 /js/**,/css/**,/image/** 허용

@Controller
public class UserController {
	
	//인증이 필요하지 않은 곳에 auth붙임
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
}
