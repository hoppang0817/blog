package com.bit.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bit.model.User;

//DAO
//자동으로 bean등록이 된다
//@repository 생략가능
public interface UserRepository extends JpaRepository<User,Integer>{//아무것도 없이 선언만 되어있음 기본적인 crud만 가능 조건이 있는 sql문이 필요하다며 함수만들어야함

	//select * from user where username=?1
	Optional<User> findByUsername(String username);

}

//JPA Naming전략
//select  * from user where username=?1 and pasword=?2
//User로 리턴해줌
//시큐리티 로그인 사용할꺼임
//User findByUsernameAndPassword(String username, String password);
