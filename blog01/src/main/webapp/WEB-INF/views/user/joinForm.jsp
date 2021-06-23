<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/hader.jsp"%>
<div class="container" style="margin-top: 50px; margin-bottom: 50px">
	<form>
		<div class="form-group">
			<label for="username">Username</label>
			<div>
			 <input type="text" class="form-control " placeholder="Enter  username" id="username" >
			 <br>
			<button onclick="index1.chckeId" type="button" class="btn btn-sm btn-primary " id="chckeId">중복확인</button>
		</div>
		</div>
		
		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password" >
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" class="form-control" placeholder="Enter email" id="email">
			<div class="valid">ex)asd@naver.com 형식으로 입력하세요</div>
		</div>
		
	</form>
		<button id="btn-save" class="btn btn-primary">회원가입</button>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


