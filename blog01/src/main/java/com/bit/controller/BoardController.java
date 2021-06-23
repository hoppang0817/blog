package com.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bit.config.auth.PrincipalDetail;
import com.bit.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	
	//컨드롤러에서 세션을  어떻게 찾나?
	//@AuthenticationPrincipal  PrincipalDetail principal
	//아무것도 안적었을때 와 /일때 여기로
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size = 5,sort="id", direction = Sort.Direction.DESC )Pageable pageable) {//데이터를 가져갈때 model을 사용한다
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index1"; 
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id , Model model) {
		boardService.조회수증가(id);
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}

}
