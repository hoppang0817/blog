package com.bit.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bit.model.User;

import lombok.Getter;
//시큐리티 로그인으로 정상적인 로그인이 이루어지고 나며 자동으로 시큐리티 세션에 저장된다
//이때 세션에 저장할수있는 타입은 UserDetails 타입이여야 함으로 User오브젝트 를 타입변경을 위함
@Getter
public class PrincipalDetail implements UserDetails {
	private User user; //콤포지션 -> 객체를 품고있다

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {//PrincipalDetail.getPassword할경우 실제 값은 user.getPassword의 값이 나와야함으로
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴한다(true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있는지 아닌지를 리턴한다 (true:안잠겨있음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되었는지 아닌지를 리턴함(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화되어있는지 아닌지를 리턴함(true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정의 권한을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors= new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole(); //스프링에서 role을 받을 때 규칙 꼭 ROLE_해줘야함
//			}
//		});
		//add에들어갈 타입은GrantedAuthority 뿐이고 GrantedAuthority안에 메소드는 하나만 존재하기때문에 
		//람다식으로 return 값만 적어줘도 됨
		collectors.add(()->{return "ROLE_"+user.getRole();});
		return collectors;
	}
	
}
