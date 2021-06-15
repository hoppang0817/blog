package com.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	//아무것도 안적었을때 와 /일때 여기로
	@GetMapping({"","/"})
	public String index() {
		return "index1";
	}
}
