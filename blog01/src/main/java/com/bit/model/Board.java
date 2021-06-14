package com.bit.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌터 패턴
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob  //대용량 데이터
	private String content;  //섬머노트 라이브러리<html>태그가 섞여서 디자인됨
	
	@ColumnDefault("0")
	private int count; //조회수
	
	//fetch = FetchType.EAGER 게시판을 select할경우 무조건 들고와야하는 데이터 
	// fetch = FetchType.LAZY 게시판을 select 할경우 들고와도 안들고와도 상관없는 데이터
	@ManyToOne(fetch = FetchType.EAGER) //Many = Board , One = User  연관관계
	@JoinColumn(name="userId")//테이블이 생성될때 변수명
	private User user; //DB는 오브젝트를 저장할 수 없다.FK,자바는 오브젝트를 저장할수있다
	
	//하나의 게시글은 여러개의 답글을 가질수있다
	@OneToMany (mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다(난 FK 아니에요) DB에 컬럼을 만들지 마세요 ->외래키가 아닌 그저 결과를 가져오기위한 수단일뿐
	private List< Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	}
