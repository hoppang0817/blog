<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/hader.jsp"%>

<div class="container" style="margin-top: 50px; margin-bottom: 50px">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}">
		<a href="/board/${board.id }/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br> <br>
	<div>
		글 번호 : <span id="id"><i>${board.id }</i></span> 글작성자 : <span><i>${board.user.username }</i></span>
	</div>
	<br>
	<div class="form-group">
		<h3>${board.title }</h3>
	</div>
	<div class="form-group">
		<div>${board.content }</div>
	</div>
	<hr />
		<div class="card">
			<form>
				<input type="hidden" value=" ${principal.user.id}" id="userId"> <input type="hidden" value="${board.id }" id="boardId">
				<div class="card-body">
					<textarea id="reply-content" rows="1" class="form-control"></textarea>
				</div>
				<div class="card-footer">
					<button type="button" class="btn btn-primary" id="btn-reply-save">등록</button>
				</div>
			</form>
		</div>
		<br>
		<div class="card">
			<div class="card-header">댓글리스트</div>
			<!-- 내가만든 id는 --넣어서구분해주는게 좋음 -->
			<ul id="reply-box" class="list-group">
				<c:forEach var="reply" items="${board.replys}">
					<li id="reply-${reply.id }" class="list-group-item d-flex justify-content-between">
						<div>${reply.content }</div>
						<div class="d-flex">
							<div class="font-italic">작성자: ${reply.user.username } &nbsp;</div>
							<c:if test="${reply.user.username == principal.user.username }">
								<button onclick="index1.replyDelete(${board.id},${reply.id })" class="badge badge-danger">삭제</button>
							</c:if>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


