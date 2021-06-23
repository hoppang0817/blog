package com.bit.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bit.model.Board;


public interface BoardRepository extends JpaRepository<Board,Integer>{//아무것도 없이 선언만 되어있음 기본적인 crud만 가능 조건이 있는 sql문이 필요하다며 함수만들어야함

	@Modifying
	@Query(value = "UPDATE board b SET b.count=b.count + 1 WHERE id=?1",nativeQuery = true)
	int updateCount(int id);
}
 