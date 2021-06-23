package com.bit.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bit.model.OAuthToken;
import com.bit.model.User;
import com.bit.model.kakaoProfile;
import com.bit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수있는 경로를 /auth/허용
// 그냥주소가 / 이면 index1.jsp허용
//static 이하에 있는 /js/**,/css/**,/image/** 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//인증이 필요하지 않은 곳에 auth붙임
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	@GetMapping("/adminOnly/adminPage")
	public String adminForm(Model model) {
		model.addAttribute("userList", userService.회원목록()); 
		return "admin/adminPage";
	}
	
	@GetMapping("/auth/profileAndAlbum")
	public String profileAndAlbumForm(Model model) {
		return "profileAndAlbum";
	}

	
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) {//데이터를 리턴해주는 컨드롤러 함수
		
		//post방식으로 key=value데이터를 요청(카카오쪽으로)
		//http요청을 편하게해주는 라이블러리
		//Retrofit2->안드로이드에서 많이사용
		//OkHttp
		//RestTemplate
		RestTemplate rt = new RestTemplate();
		
		//HttpHeaders 오브젝트
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type"," application/x-www-form-urlencoded;charset=utf-8");
		
		//body데이터를 담을 오브젝트
		MultiValueMap<String, String>params = new LinkedMultiValueMap<>();
		//변수화 시켜서 사용하는게 좋음
		params.add("grant_type", "authorization_code");
		params.add("client_id", "5daa0394809f9129726c6a6960a5a54f");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//헤더와 바디를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>>kakaoTokenRequest =
				new HttpEntity<>(params,headers);
		
		//실제 요청
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", //토큰 발급 요청주소
				HttpMethod.POST, //요청메소드
				kakaoTokenRequest,//http 헤더값과 바디값
				String.class //응답을 받을 타입
				);
		
		//json데이터를 오브젝트에 담기 -> 응답을 json으로 해줌으로
		//Gson,Json Simple.ObjectMapper 라이브러리들
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken =null;
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		}catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	RestTemplate rt2= new RestTemplate();
		
		//HttpHeaders 오브젝트
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type"," application/x-www-form-urlencoded;charset=utf-8");
		
		//헤더와 바디를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>>kakaoProfileRequest2 =
				new HttpEntity<>(headers2);
		
		//실제 요청
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", //토큰 발급 요청주소
				HttpMethod.POST, //요청메소드
				kakaoProfileRequest2,//http 헤더값과 바디값
				String.class //응답을 받을 타입
				);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		kakaoProfile kakaoProfile =null;
		try {
			kakaoProfile = obMapper2.readValue(response2.getBody(), kakaoProfile.class);
		}catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("카카오 아이디: "+kakaoProfile.getId());
		System.out.println("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : "+ kakaoProfile.getKakao_account().getEmail() + "_"+ kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		//UUID는 중복되지않는 어떤 특정값
		System.out.println("블로그 서버 비빌번호 : "+cosKey);
		
		User kakaoUser = User.builder()
				.username( kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//이미 가입된사용자인지 비가입자인지 구분
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("기존회원이 아닙니다...............");
			userService.회원가입(kakaoUser);
		}
		System.out.println("자동로그인 합니다.");
		//로그인처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
}
