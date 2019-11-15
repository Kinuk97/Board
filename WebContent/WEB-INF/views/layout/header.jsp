<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>☆Kinuk☆</title>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<!-- 부트스트랩 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<style type="text/css">
#header, #footer {
	background-color: #29fff1b8;
	margin: 0 auto;
	padding: 10px;
	min-height: auto;
}

#header h2 {
	padding: 10px;
	margin: 0;
}

.container {
	background-color: #f9f1f157;
	padding: 10px;
	min-height: 600px;
}
</style>
</head>
<body>
	<div id="header" class="text-center container">
		<h2>Kinuk</h2>
		<div class="text-right">
		<a class="btn btn-success" href="/main" role="button">메인으로 가기</a>
		<a class="btn btn-primary" href="/board/list" role="button">게시판</a>
			<c:choose>
				<c:when test="${login }">
			${userid }(${usernick })님 환영해요~ 
			<a class="btn btn-success" href="/member/logout" role="button">로그아웃</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-success" href="/member/login" role="button">로그인</a>
					<a class="btn btn-primary" href="/member/join" role="button">회원가입</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div id="wrapper">