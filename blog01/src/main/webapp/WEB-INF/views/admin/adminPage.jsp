<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../layout/hader.jsp"%>
<div class="container">
		<div class="board_wrap panel">
			<div class="title_wrap">
				<p class="title">
					<span class="point"></span>USERLIST<span class="point"></span>
				</p>
			</div>
		</div>
		<table class="table table"  style=" margin-bottom: 70px">
			<thead>
				<tr>
					<th>Id</th>
					<th>userName</th>
					<th>email</th>
					<th>기타</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="user"  items="${userList}" >
				<tr>
					<td>${user.id}</td>
					<td>${user.username }</td>
					<td>${user.email }</td>
					<td><button onclick="index1.userDelete(${user.id})" class="badge badge-danger">탈퇴</button></td>
				</tr>
				</c:forEach>
			</tbody> 
		</table>
</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


