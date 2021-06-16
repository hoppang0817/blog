package com.bit.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.blog.repository.UserRepository;
import com.bit.model.User;

@Service //이때 Bean에 등록됨
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인요청을 가로챌때 ,username,password 변수 2개를 가로 챔
	//password처리는 알아서해줌
	//username이 DB에 있는지 확인만 해주면됨
//만들지 않으면 DB에있는 사용자로 로그인말구 기본적으로 제공되는 user 와 비밀번호 로 로그인가능함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: "+ username);
				});
		return new PrincipalDetail(principal); //시큐리티 세션에 유저 정보가 저장됨 (이때 타입은 userDetail타입)
	}

}
