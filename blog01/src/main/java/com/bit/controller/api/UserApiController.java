package com.bit.controller.api;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.config.auth.PrincipalDetail;
import com.bit.dto.ResponsDto;
import com.bit.model.RoleType;
import com.bit.model.User;
import com.bit.service.UserService;

//데이터만 리터함으로
@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	//session객체를 스프링 컨테이너가 Bean으로 등록해서 가지고있다
//	//함수의 매개변수로 적어 사용해도됨
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponsDto<Integer> save(@RequestBody User user ) {
		System.out.println("save 호출됨");
		//실제 db에 insert 가 수행되고 return으로.
		userService.회원가입(user);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);//정상적인 응답이면 (200,1)이 됨
	}
	
	@PutMapping("/user")
	public ResponsDto<Integer>update(@RequestBody User user){//@RequestBody사용하지 않으면 json데이터 못받음
		userService.수정하기(user);
		//여기서는 트랜잭션종료로 DB의 값은 변경되었지만 세션에 담겨있는 정보는 변경되지않았음
		//그래서 변경이 완료되도 화면에서는 확인이 되지않음 따라서 강제로 세션 정보를 변경해야함
		//세션등록-> 강제로 로그인하여 Authentication생성해서 로그인유지및 세션안 유저 정보변경
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@GetMapping("/auth/user/chckeId/{username}")
	public ResponsDto<Integer>chckeId(@PathVariable String username){
		System.out.println("username : "+username);
		User reslut = userService.회원찾기(username);
		System.out.println(reslut.getId());
		System.out.println(reslut.getUsername());
		int num =0;
		if(reslut.getUsername()==null) {
			num =200;
		}else {
			num=500;
		}
		System.out.println(num);
		return new ResponsDto<Integer>(num, 1);
	}
	
	@DeleteMapping("/adminOnly/adminPage/{userId}")
	public ResponsDto<Integer> deleteId(@PathVariable int userId){
		userService.탈퇴하기(userId);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
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
