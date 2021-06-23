package com.bit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bit.config.auth.PrincipalDetailService;

//시큐리티 로그인
//빈 등록->스프링컨테이너에서 객체를 관리 할수있게하는거
@Configuration  //빈등록
@EnableWebSecurity  //시큐리티 필터  등록 => 활성화된 시큐리티에서 auth으로 들어온건지 아닌지를 구분 인증의 필요를 구분해준다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소를 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻 
//위에 3개의 시큐리티 관련 언노테이션은 세트임 모르겟음 걍쓰셈
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //빈등록 어디서든 사용가능
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //IoC가 됨 -> 해쉬로 변경하는 이 메소드르 스프링이 관리함
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 로그인 요청시 가로채서 로그인을 하는데 이때 작성된 비밀번호가 뭘로 해쉬가 되어 회원가입 되었는지 알아야
	//같은 해쉬로 암호화 해서 DB에있는 비밀번호와 비교할수있다
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  //csrf 토큰 비활성화 
			.authorizeRequests()
				.antMatchers("/adminOnly/**").hasAuthority("ROLE_ADMIN")//ADMIN인 경우만 허용
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") //모두에게 허용 
				.permitAll()
				.anyRequest() // 모두에게 허용 되는 페이지가 아닌 다른 모든 페이지는
				.authenticated()// 인증이 필요하다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")//인증이 필요한 모든 요청은 해당 페이지로감
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당주소로 요청오는 로그인을 가로채서 대신 로그인을 해줌
				.defaultSuccessUrl("/");//요청이 성공적이라면 해당주소로 이동
				
	}
}
// 로그인 과정-
// /auth/loginProc 로들어온 로그인요청을 시큐리티가 가로챈다->가로챈 정보를 PrincipalDetailService로 보냄-> username을 비교해줌->
// 비교한 값은 userDetailsService 로감 -> 여기서  사용자가 적은 비밀번호를 passwordEncoder로 인코딩해주고 DB에있는 비밀번호와비교해줌-> 정상이면 시큐리티 세션에 유저정보가 저장됨