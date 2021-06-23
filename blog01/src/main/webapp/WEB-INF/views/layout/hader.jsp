<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- 로그인 완료시 principal 이라는 변수에 회원정보등록 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link href="<c:url value="/css/All.css"/>"  rel="stylesheet">
</head>
<body>
	<div id="header">
		<div class="contains container">
			<h1 class="logo">
				<a href="/"> <img src="/image/logo.png">
				</a>
			</h1>
			<ul id="gnb">
				<li><a href="/">HOME</a></li>
				<li><a href="/auth/profileAndAlbum">PROFILE & ALBUM</a></li>
				<c:choose>
					<c:when test="${empty principal }">
						<li><a href="/auth/loginForm">LOGIN</a></li>
						<li><a href="/auth/joinForm">JOIN</a></li>
					</c:when>
					<c:when test="${principal.user.role == 'ADMIN' }">
						<li><a href="/adminOnly/adminPage">ADMINPAGE</a></li>
						<li><a href="/board/saveForm">WRITING</a></li>
						<li><a href="/user/updateForm">MYPAGE</a></li>
						<li><a href="/logout">LOGOUT</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/board/saveForm">WRITING</a></li>
						<li><a href="/user/updateForm">MYPAGE</a></li>
						<li><a href="/logout">LOGOUT</a></li>
						<!-- 시큐리티 로그아웃은  /logout로 디폴트 되어있음-->
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
