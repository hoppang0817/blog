<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="layout/hader.jsp"%>
<div id="content" >
	<div class="main_visual">
		<img src="/image/main_visual.png" class="img">
	</div>
</div>
<div class="container">
	<div class="container">
		<div class="board_wrap panel">
			<div class="title_wrap">
				<p class="title">
					<span class="point"></span>BOARD<span class="point"></span>
				</p>
			</div>
		</div>
		<table class="table table">
			<thead>
				<tr>
					<th>No</th>
					<th>Title</th>
					<th>Nickname</th>
					<th>hit</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="board" items="${boards.content }">
					<tr>
						<td>${board.id}</td>
						<td><a href="/board/${board.id}" id="detail">${board.title }</a></td>
						<td>${board.user.username }</td>
						<td>${board.count }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul  id="pagebtn"  class="pagination justify-content-center" >
			<c:choose>
				<c:when test="${boards.first }">
					<li class="page-item  disabled"><a class="page-link" href="?page=${boards.number -1}">Previous</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item "><a class="page-link" href="?page=${boards.number -1}">Previous</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${boards.last }">
					<li class="page-item disabled"><a class="page-link" href="?page=${boards.number +1}">Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${boards.number +1}">Next</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>
<%@ include file="layout/footer.jsp"%>


