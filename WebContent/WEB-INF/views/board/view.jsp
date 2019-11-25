<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$("#btnSpan").on("click", "button#recommendBtn",function() {
		$.ajax({
			type : "GET",
			url : "/board/recommend",
			data : { "boardno" : $("#boardno").val() },
			dataType : "text",
			success : function(data) {
				var result = JSON.parse(data);
			
				if (result.check) {
					$("#recommendBtn").text("추천취소");
					$("#recommendBtn").attr("id", "unrecommendBtn");
					// show() hide()
				}
				
				$("#cntRecommend").text(result.cnt);
			},
			error : function(e) {
				console.log(e);
			}
		});
	});
	
	$("#btnSpan").on("click", "button#unrecommendBtn", function() {
		$.ajax({
			type : "GET",
			url : "/board/unrecommend",
			data : { "boardno" : $("#boardno").val() },
			dataType : "text",
			success : function(data) {
				var result = JSON.parse(data);
				if (!result.check) {
					$("#unrecommendBtn").text("추천하기");
					$("#unrecommendBtn").attr("id", "recommendBtn");
					// show() hide()
				}
				
				$("#cntRecommend").text(result.cnt);
			},
			error : function(e) {
				console.log(e);
			}
		});
	});
	
	$("#btnSpan").on("click", "button#nologinBtn", function() {
		alert("로그인을 해주세요~");
	});
});
</script>

<div class="container">
	<h1 class="text-center">게시글 디테일</h1>
	<hr>
	<table class="table" style="margin: 0 auto;">
		<thead>
			<tr>
				<td>게시글번호</td>
				<td>제목</td>
				<td>작성자</td>
				<td>조회수</td>
				<td>작성일</td>
			</tr>
		</thead>
		<tr>
			<td>${board.boardno }</td>
			<td>${board.title }</td>
			<td>${board.id }</td>
			<td>${board.hit }</td>
			<td>${board.writtendate }</td>
		</tr>
		<tr>
			<td colspan="5">
				<div class="col-md-12" style="border: 1px solid #CCC; height: 500px;">${board.content }</div>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<c:if test="${!empty file.fileno}">
					<a href="/board/file?fileno=${file.fileno }">${file.originname } (용량 : ${file.filesize })</a>
				</c:if>
			</td>
			<td colspan="2" style="text-align: right" id="menuTd">
				<div style="display: inline-block; height: fit-content; margin: 0 5px; border: 1px solid #CCC;
				border-bottom-left-radius: 5px; border-top-left-radius: 5px;">
					<span id="btnSpan">
					<c:choose>
						<c:when test="${!empty check && !check }">
							<button class="btn btn-primary" style="border-top-right-radius: 0; border-bottom-right-radius: 0;" id="recommendBtn">
								추천하기
							</button>
						</c:when>
						<c:when test="${check }">
							<button class="btn btn-primary" style="border-top-right-radius: 0; border-bottom-right-radius: 0;" id="unrecommendBtn">
								추천취소
							</button>
						</c:when>
						<c:when test="${empty check }">
							<button class="btn btn-primary" style="border-top-right-radius: 0; border-bottom-right-radius: 0;" id="nologinBtn">
								추천하기
							</button>
						</c:when>
					</c:choose>
					</span>
					<b style="padding-right: 10px; padding-left: 8px;" id="cntRecommend">${board.recommend }</b>
				</div>
				<a class="btn btn-default" href="/board/list" role="button">목록</a>
				<c:if test="${board.id == userid }">
					<a class="btn btn-default" href="/board/delete?boardno=${board.boardno }" role="button">삭제</a>
					<a class="btn btn-default" href="/board/update?boardno=${board.boardno }" role="button">수정</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<form action="/comment/insert" method="get">
					<textarea class="form-control" style="resize: none; width: 95%; display: inline; float: left;" name="content" required="required"></textarea>
					<button class="btn" style="height: 54px; width: 5%; padding: 0;">작성</button>
					<input type="hidden" value="${board.boardno }" name="boardno">
				</form>
			</td>
		</tr>
		<c:forEach items="${comment }" var="com">
			<tr>
				<td>작성자 : ${com.userid }</td>
				<td colspan="4">
					<div style="height: 54px;" class="col-md-11 col-sm-11">${com.content }</div>
					<div class="col-md-1 col-sm-1 text-right">
						<a class="btn btn-info" href="/comment/delete?commentno=${com.commentno }&boardno=${board.boardno}">삭제</a>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<input type="hidden" value="${board.boardno }" id="boardno">

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>