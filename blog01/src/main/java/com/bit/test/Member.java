package com.bit.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor //빈생성자
public class Member {

	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder //빌더를 사용하면 여러개의 생서자를 만들지 않아도 됨, 순서상관없이 사용가능
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	
}
