<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/hader.jsp"%>
<div class="container" style="margin-top: 50px; margin-bottom: 50px">
<input type="hidden"  id="id" value="${principal.user.id }">
	<form>
		<div class="form-group">
			<label for="username">Username</label> <input type="text" class="form-control" placeholder="Enter  username" id="username" readonly="readonly" value="${principal.user.username }">
		</div>
		<c:if test="${empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password"  >
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email }">
		</div>
		</c:if>
		
	</form>
		<button id="btn-update" class="btn btn-primary">수정하기</button>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


