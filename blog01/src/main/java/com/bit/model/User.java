package com.bit.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어) Object 생성하면->테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌터 패턴
@Entity //User클래스가 자동으로 db에 테이블생성
//@DynamicInsert  // insert시 null인 필드를 제외 시켜준다
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //auto_increment
	
	@Column(nullable = false, length = 100, unique = true) //null이될수없다 , 길이는30까지
	private String username; //아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	
    //@ColumnDefault("'user")
	//DB에는 RoleType이라는게 없다.
	//@Enumerated 어노테이션을 사용하여 role타입이 string이라고 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋음.->사용하게되면 도메인(어떤 범위)설정가능=3가지중 하나를 선택할수있게 할수있음 // 가입할시 권한정도 admin,user,manager
	
	private String oauth;  //kakao,google로그인한사람들 구분
	
	@CreationTimestamp //시간이 자동으로 입력
	private Timestamp createDate;
	
}
