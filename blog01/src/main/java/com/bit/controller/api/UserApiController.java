package com.bit.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	
	@PostMapping("/api/user")
	public ResponsDto<Integer> save(@RequestBody User user) {
		System.out.println("save 호출됨");
		//실제 db에 insert 가 수행되고 return으로.
		user.setRole(RoleType.USER);
		int result = userService.회원가입(user);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), result);
	}
}
