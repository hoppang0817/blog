package com.bit.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.bit.dto.ResponsDto;

@ControllerAdvice //모든 exception 이발생하면 여기로옴
@RestController
public class GlobalExceptionHandler {

	//IllegalArgumentException이 발생하면 메소드가 실행
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponsDto<String> handleArgumentException(IllegalArgumentException e) {
		return new  ResponsDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
