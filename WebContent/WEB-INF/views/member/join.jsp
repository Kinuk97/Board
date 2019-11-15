<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/layout/header.jsp"%>


<div class="container text-center">
	<h1>회원가입</h1>
	<hr>
	<div class="col-md-3"></div>
	<div class="col-md-6">
		<form action="/member/join" method="post">
			<div class="form-group">
				<label for="userid" class="col-md-3">아이디</label>
				<input type="text" class="form-control" id="userid" name="userid" placeholder="아이디를 입력하세요" style="width: 75%;">
			</div>
			<div class="form-group">
				<label for="usernick" class="col-md-3">닉네임</label>
				<input type="password" class="form-control"  id="usernick" name="usernick" placeholder="닉네임" style="width: 75%;">
			</div>
			<div class="form-group">
				<label for="userpw" class="col-md-3">비밀번호</label>
				<input type="password" class="form-control"  id="userpw" name="userpw" placeholder="암호" style="width: 75%;">
			</div>
			<button type="submit" class="btn btn-default">가입하기</button>
		</form>
	</div>
	<div class="col-md-3"></div>

</div>






<%@ include file="/WEB-INF/views/layout/footer.jsp"%>