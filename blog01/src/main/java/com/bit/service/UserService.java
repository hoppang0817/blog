package com.bit.service;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.blog.repository.UserRepository;
import com.bit.model.RoleType;
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
	
	@Autowired //비밀번호를 암호화 해주기위함
	private BCryptPasswordEncoder encoder;
	

	
	//회원가입이 하나의 트랜잭션으로 묶임
	@Transactional
	public void 회원가입(User user) {
			String rawPassword = user.getPassword(); //원 비밀번호
			String encPassword = encoder.encode(rawPassword); //헤쉬로 인코드한 비밀번호
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);
	}

	@Transactional
	public void 수정하기(User user) {
		//수정시에는 영속성 컨텍스트에 user오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정
		//영속화된 오브젝트를 변경하면 트랜잭션이 끝난순간 자동으로 DB에 update문을 날려줌
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원찾기 실패 : 아이디를 찾을 수 없습니다");
				});
		// 새로 작성한 비밀번호를 암호화해주어야함
		//암호화 하지않으면 시큐리티가 받아주지않음
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {//만약 카카오로그인은로 oauth에 내용이있다면 여기에 들어올수없음
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		}
	
	}
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	//관리자페이지->회원관리를 위한 목록
	@Transactional(readOnly = true)
	public List<User> 회원목록() {
		return userRepository.selectUserList();
	}
	
	@Transactional
	public void 탈퇴하기(int userId) {
		userRepository.deleteById(userId);
	}
}
//시큐리티 로그인 사용
//	@Transactional(readOnly = true) //select할때 트랜잭션이 시작, 서비스 종료시에 트랜잭션 종료(정합성 유지)
//	public User 로그인(User user) {
//		return  userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
