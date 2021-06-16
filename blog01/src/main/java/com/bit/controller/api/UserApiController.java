package com.bit.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.dto.ResponsDto;
import com.bit.model.RoleType;
import com.bit.model.User;
import com.bit.service.UserService;

//데이터만 리터함으로
@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	

	
//	@Autowired
//	//session객체를 스프링 컨테이너가 Bean으로 등록해서 가지고있다
//	//함수의 매개변수로 적어 사용해도됨
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponsDto<Integer> save(@RequestBody User user) {
		System.out.println("save 호출됨");
		//실제 db에 insert 가 수행되고 return으로.
		userService.회원가입(user);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);//정상적인 응답이면 (200,1)이 됨
	}
	
	//시큐리티 로그인 할꺼임
//	@PostMapping("/api/user/login")
//	public ResponsDto<Integer>login(@RequestBody User user){
//		System.out.println("login 호출됨");
//		User principal = userService.로그인(user); //principal->접근주체
//		if(principal != null) {
//			session.setAttribute("principal", principal);//session안에 principal 담음
//		}
//		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
//	}
}
