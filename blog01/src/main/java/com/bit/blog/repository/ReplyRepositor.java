package com.bit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bit.model.Reply;

public interface ReplyRepositor extends JpaRepository<Reply, Integer> {

	@Modifying
	@Query(value = "INSERT INTO reply(userId,boardId,content,createDate) VALUES(?1,?2,?3,now())",nativeQuery = true)
	int mSave(int userId, int boardId, String content);
}