<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<script src="//cdn.ckeditor.com/4.13.0/standard/ckeditor.js"></script>

<div class="container text-center">
	<h1>글 수정</h1>
	<hr>
	<div class="col-md-2"></div>
	<div class="col-md-8">
		<form action="/board/update" method="post" enctype="multipart/form-data">
			<div class="form-group" style="padding: 30px;">
				<label for="title" class="col-md-2" style="margin-top: 8px;">제목</label>
				<input type="text" class="col-md-10 form-control" id="title" name="title" placeholder="제목을 입력하세요"
				 style="width: 75%; display: inline;" value="${board.title }">
			</div>
				<textarea name="content" id="content" rows="10" cols="80">${board.content }</textarea>
	            <script>
	                CKEDITOR.replace( 'content' );
	            </script>
			<div class="form-group text-left" style="margin-top: 5px;">
			    <input type="file" id="upfile" name="upfile">
			    <p class="help-block">파일은 10MB까지만 업로드 가능합니다.<br>원본 파일 : ${file.originname }</p>
  			</div>
  			<input type="hidden" name="userid" id="userid" value="${userid }">
  			<input type="hidden" value="${board.boardno }" name="boardno">
			<button type="submit" class="btn btn-default" id="btnWrite">작성하기</button>
		</form>
	</div>
	<div class="col-md-2"></div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp"%>