<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/layout/header.jsp"%>


<div class="container text-center">
	<h1>로그인</h1>
	<hr>
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<form action="/member/login" method="post">
			<div class="form-group">
				<label for="userid" class="col-md-3">아이디</label>
				<input type="text" class="form-control" id="userid" name="userid" placeholder="아이디를 입력하세요" style="width: 75%;">
			</div>
			<div class="form-group">
				<label for="userpw" class="col-md-3">비밀번호</label>
				<input type="password" class="form-control"  id="userpw" name="userpw" placeholder="암호" style="width: 75%;">
			</div>
			<c:if test="${!empty login_failed && !login_failed }">
				<div style="color: red;">아이디 혹은 비밀번호를 확인해주세요...</div>
			</c:if>
			<button type="submit" class="btn btn-default">로그인</button>
			<a class="btn btn-primary" href="/member/join" role="button">회원가입</a>
		</form>
	</div>
	<div class="col-md-3"></div>

</div>






<%@ include file="/WEB-INF/views/layout/footer.jsp"%>