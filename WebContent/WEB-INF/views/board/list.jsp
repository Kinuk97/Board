<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery.com의 CDN을 이용한 설치(배포) -->
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script type="text/javascript">
$(document).ready(function() {
	$(".boardLine").on("click", function() {
		location.href = "/board/view?boardno=" + $(this).find("td").eq(0).text();
	});
});
</script>
<style type="text/css">
.boardLine {
	cursor: default;
}
</style>
</head>
<body>
	<h1 style="text-align: center;">게시글 리스트</h1>
	<hr>
	<div class="container">
		<table class="table table-hover" style="margin: 0 auto;">
			<thead>
				<tr>
					<td>게시글번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>조회수</td>
					<td>작성일</td>
				</tr>
			</thead>
			<c:forEach items="${boardList }" var="board">
				<tr class="boardLine">
					<td>${board.boardno }</td>
					<td>${board.title }</td>
					<td>${board.id }</td>
					<td>${board.hit }</td>
					<td>${board.writtendate }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>