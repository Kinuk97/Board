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
// 	$("tbody tr").on("click", "td:not([:nth-child(1)])", function() {
// 		location.href = "/board/view?boardno=" + $(this).find("td").eq(0).text().trim();
// 	});
	
	$("#selectAll").on("click", function() {
		if ($("#selectAll").prop("checked")) {
			$("input[name=deleteBoard]").prop("checked", true);
		} else {
			$("input[name=deleteBoard]").prop("checked", false);
		}
	});
});
</script>
	<div class="container">
		<div class="col-md-12 text-center" style="margin: 5px 0;">
			<h1 class="text-center" style="display: inline;">게시글 리스트</h1>
			<div class="text-right">
				<form action="/board/list" method="get" style="display: inline;">
					<input name="search" class="form-control" type="text" placeholder="검색" style="width: 300px; display: inline;">
					<button class="btn btn-primary">검색</button>
				</form>
			</div>
		</div>
		<hr>
		<form action="/board/listDelete" method="post">
			<button class="btn btn-warning" style="margin-bottom: 5px;">삭제하기</button>
			<table class="table table-hover table-condensed" style="border-bottom: 1px solid #CCC;">
				<thead>
					<tr>
						<td><input type="checkbox" id="selectAll"></td>
						<td class="col-md-1">게시글번호</td>
						<td class="col-md-6" style="text-align: center;">제목</td>
						<td class="col-md-2">작성자</td>
						<td class="col-md-1">조회수</td>
						<td class="col-md-1">추천수</td>
						<td class="col-md-1">작성일</td>
					</tr>
				</thead>
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td><input type="checkbox" name="deleteBoard" value="${board.boardno }"></td>
						<td>${board.boardno }</td>
						<td><a href="/board/view?boardno=${board.boardno }">${board.title }</a></td>
						<td>${board.id }</td>
						<td>${board.hit }</td>
						<td>${board.recommend }</td>
						<td><fmt:formatDate value="${board.writtendate }" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<jsp:include page="/WEB-INF/views/layout/paging.jsp"></jsp:include>
	</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>