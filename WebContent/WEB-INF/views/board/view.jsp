<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ include file="/WEB-INF/views/layout/header.jsp" %>

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
			<td colspan="2" style="text-align: right">
				<a class="btn btn-default" href="/board/list" role="button">목록</a>
				<a class="btn btn-default" href="/board/delete?boardno=${board.boardno }" role="button">삭제</a>
				<a class="btn btn-default" href="/board/update?boardno=${board.boardno }" role="button">수정</a>
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>