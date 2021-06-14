package com.bit.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청->응답(HTML파일)
//@Controller

//사용자가 요청 ->응답(DATE)
@RestController
public class HttpControllerTest {

	//브라우저에서 요청은 get방식만 가능 
	//http://localhost:8080/http/get(select)
	@GetMapping("/http/get")
	public String getTest(Member m){
		return "get요청" + m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
		
	}

	//http://localhost:8080/http/post(insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody String text){
		return "post요청"+ text;
		
	}
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(){
		return "put요청";
		
	}
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest(){
		return "delete 요청";
		
	}
}
