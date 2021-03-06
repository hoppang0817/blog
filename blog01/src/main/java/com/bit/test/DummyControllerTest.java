package com.bit.test;

import java.util.List;
import java.util.function.Supplier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.blog.repository.UserRepository;
import com.bit.model.RoleType;
import com.bit.model.User;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입
	public UserRepository userRepository;

	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다 .해당 아이디는 db에없습니다";
		}
		return "삭제되었습니다 " + id;
		
	}
	
	//email,password 수정
	//@RequestBody -> json 타입으로 받을려면 사용해야함
	//save 함수는 id 를 전달하지 않으면 insert를 해주고
	//save 함수는 id 를 전달하면 해당 id 에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id 를 전달하면 해당 id 에 대한 데이터가 없으면 insert를 해준다
	
	@Transactional //메소드가 끝나며 자동 commit됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id , @RequestBody User requestUser) {//json데이터를 요청 -> Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아와줌)
		System.out.println("id : "+id);
		System.out.println("password : "+ requestUser.getPassword());
		System.out.println("email : "+ requestUser.getEmail());
		
		//유저 id로 유저 정보를 가지고와 user에 담는다
		//이때 영속화 됨
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정 실패");
		});
		//user에 담겨있는 정보를 변경한다
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//requestUser.setId(id);
		//변경된 정보를 save
		//userRepository.save(user);

		return user;
		
	}
	
	//전체 데이터를 받아오는거라 특정 파라메터 없어도됨
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴 받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2,sort="id", direction = Sort.Direction.DESC )Pageable pageable){
		Page<User>pagingUsers = userRepository.findAll(pageable);
		
		List<User> users = pagingUsers.getContent();
		return users;
		
	}
	
	
	//{id}주소로 파라메터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/2
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//만약 user/4라고 select 할경우 데이터베이스에서 못찾아오게 되면 null 이됨으로 
		//프로그램에 문제가 발생할수있으므로  Optional로 User객체를 감싸서 데이터를 가져올테니 null인지 아닌지 판단하여 return하여라!!
		//Optional이 제공하는 함수 종류 
		//.get()-> 나는 null이 나올리없으니 return user로해
		//.orElseGet(new Supplier<User>) -> null이아닌경우 정상적인 return을 null인 경우 오버라이드 받은 메소드로 들어감
		//.orElseThrow(new Supplier<IllegalArgumentException>() -> null인경우 오버라이드 받은 메소드로 들어가 오류 내용을 작서하여 리턴해줌
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id  : " + id);
			}
		});
		//요청: 웹브라우저
		//user객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수있는 데이터) ->json
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
	
	
//	@PostMapping("/dummy/join")
//	public String join(String username, String password,String email) {//key = value(약속된 교칙)
//		System.out.println("username : "+ username);
//		System.out.println("password : "+ password);
//		System.out.println("email : "+ email);
//		return "회원가입이 완료되었습니다.";
//	}

	@PostMapping("/dummy/join")
	public String join(User user) {//key = value(약속된 교칙)
		System.out.println("id : "+ user.getId());
		System.out.println("username : "+ user.getUsername());
		System.out.println("password : "+ user.getPassword());
		System.out.println("email : "+ user.getEmail());
		System.out.println("role : "+ user.getRole());
		System.out.println("createDate : "+ user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
