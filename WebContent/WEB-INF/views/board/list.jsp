<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<style type="text/css">
table tr {
	cursor: default;
	text-align: center;
}

tr td:nth-child(2) {
	text-align: left;
}

table thead {
	background-color: #cec4c4;
}
/* tr td:not(:first-child), thead tr td:not(:first-child) { */
/* 	border-left: 1px solid black; */
/* } */
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("tbody tr").on("click", function() {
		location.href = "/board/view?boardno=" + $(this).find("td").eq(0).text();
	});
});
</script>
	<div class="container">
		<h1 class="text-center">게시글 리스트</h1>
		<hr>
		<table class="table table-hover table-condensed">
			<thead>
				<tr>
					<td class="col-md-1">게시글번호</td>
					<td class="col-md-6" style="text-align: center;">제목</td>
					<td class="col-md-2">작성자</td>
					<td class="col-md-1">조회수</td>
					<td class="col-md-2">작성일</td>
				</tr>
			</thead>
			<c:forEach items="${boardList }" var="board">
				<tr>
					<td>${board.boardno }</td>
					<td>${board.title }</td>
					<td>${board.id }</td>
					<td>${board.hit }</td>
<%-- 					<td>${board.writtendate }</td> --%>
					<td><fmt:formatDate value="${board.writtendate }" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
				</tr>
			</c:forEach>
		</table>
		<jsp:include page="/WEB-INF/views/layout/paging.jsp"></jsp:include>
	</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>