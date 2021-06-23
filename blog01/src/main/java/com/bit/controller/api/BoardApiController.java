package com.bit.controller.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.config.auth.PrincipalDetail;
import com.bit.dto.ReplySaveRequestDto;
import com.bit.dto.ResponsDto;
import com.bit.model.Board;
import com.bit.model.Reply;
import com.bit.service.BoardService;

//데이터만 리터함으로
@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponsDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {//title,content,글쓴이 세션에담긴 정보
		System.out.println("save 호출됨");
		boardService.글쓰기(board,principal.getUser());
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);//정상적인 응답이면 (200,1)이 됨
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponsDto<Integer>deleteById(@PathVariable int id){
		boardService.삭제하기(id);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponsDto<Integer>update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정하기(id,board);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
//	//데이터를 받을때 dto를 만들어서 받는게 좋음
//	@PostMapping("/api/board/{boardId}/reply")
//	public ResponsDto<Integer>replySave(@PathVariable int boardId ,@RequestBody Reply reply,@AuthenticationPrincipal PrincipalDetail principal){
//		boardService.댓글작성(principal.getUser(),boardId,reply);
//		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	//데이터를 받을때 dto를 만들어서 받는게 좋음
	@PostMapping("/api/board/{boardId}/reply")
	public ResponsDto<Integer>replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){
		boardService.댓글작성(replySaveRequestDto);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	//여기서 boardId는 그저 주소를 만들기 위해 받아온것임 
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponsDto<Integer>replydelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponsDto<Integer>(HttpStatus.OK.value(), 1);
	}
	 
}
