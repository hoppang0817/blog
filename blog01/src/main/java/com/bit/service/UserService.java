package com.bit.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.blog.repository.UserRepository;
import com.bit.model.User;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해 줌.Ioc를 해준다
//서비스란?
//서비스는 하나의 트랜잭션을 가지거나 하나이상의 트랜잭션을 묶어 하나의 트랜잭션으로 서비스화 할수있음
//ex) 송금서비스(A의 통장에서 -3만원 update(), B의 통장에서 +3만원 update()) => 2개의 update()가 사용되는데 이것을 하나의 트랜직션로 만들어줌
//**트랜잭션을 일을 처리하는 하나하나의 작은 단위(?)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	//회원가입이 하나의 트랜잭션으로 묶임
	@Transactional
	public Integer 회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원가입(): "+ e.getMessage());
		}
		return -1;
	}
}
