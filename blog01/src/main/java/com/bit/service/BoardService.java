package com.bit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.blog.repository.BoardRepository;
import com.bit.blog.repository.ReplyRepositor;
import com.bit.blog.repository.UserRepository;
import com.bit.dto.ReplySaveRequestDto;
import com.bit.model.Board;
import com.bit.model.Reply;
import com.bit.model.User;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepositor replyRepositor;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void 글쓰기(Board board,User user ) {//title,content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {//title,content
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public void 조회수증가(int id) {
		boardRepository.updateCount(id);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {//option을 리턴함
		//board는이미 reply의 정보를 들고있음 즉 board를 id로 찾기하면 reply정보도 함께select됨
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패 : 아이디를 찾을 수 없습니다");
				});
	}
	
	@Transactional
	public void 삭제하기(int id) { 
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id) //영속화 시켜야함
				.orElseThrow(()->{
					return new IllegalArgumentException("글찾기 실패 : 아이디를 찾을 수 없습니다");
				});//영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당함수가 종료시 트랜잭션이 종료된다 이때 더티체킹이 일어나 자동업데이트(commit) 됨
	}
	
	@Transactional
	public void 댓글작성(ReplySaveRequestDto replySaveRequestDto) {
	
		int reply = replyRepositor.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println(reply);
	}
	
	
//		User user = userRepository.findById(replySaveRequestDto.getUserId())
//				.orElseThrow(()->{
//					return new IllegalArgumentException("댓글쓰기 실패: 유저id를 찾을 수 없습니다");
//				});//영속화
//		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
//				.orElseThrow(()->{
//					return new IllegalArgumentException("댓글쓰기 실패: 글을 찾을 수 없습니다");
//				});//영속화-> 보드아이디를 이용해 작성된 글이있는지 확인
//		
//		//빌더를 이용해 추가
//		Reply reply = Reply.builder()
//				.user(user)
//				.board(board)
//				.content(replySaveRequestDto.getContent())
//				.build();
//		
	
//		requestReply.setUser(user);
//		requestReply.setBoard(board);
		//replyRepositor.save(reply);

	@Transactional
	public void 댓글삭제(int id) {
		replyRepositor.deleteById(id);
	}
}
