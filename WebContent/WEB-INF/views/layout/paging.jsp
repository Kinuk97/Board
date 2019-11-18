<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-3"></div>
<div class="text-center col-md-6">
  <ul class="pagination">
	<!-- 처음으로 가기 -->
   	<c:if test="${paging.curPage ne 1 }">
	    <li>
	      <a href="/board/list" aria-label="Previous">
	        <span aria-hidden="true">&laquo;&laquo;</span>
	      </a>
	    </li>
   	</c:if>
    <!-- 이전 페이징 리스트 가기 -->
   	<c:if test="${paging.startPage > paging.pageCount }">
	    <li>
	      <a href="/board/list?curPage=${paging.startPage - paging.pageCount }" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
   	</c:if>
   	<c:if test="${paging.startPage < paging.pageCount }">
	    <li>
	      <a href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
   	</c:if>
<%--    	<c:if test="${paging.curPage ne 1 }"> --%>
<!-- 	    이전 페이지로 가기 -->
<!-- 	    <li> -->
<%-- 	      <a href="/board/list?curPage=${paging.curPage - 1 }" aria-label="Previous"> --%>
<!-- 	        <span aria-hidden="true">&laquo;</span> -->
<!-- 	      </a> -->
<!-- 	    </li> -->
<%--    	</c:if> --%>
    <!-- 페이징 리스트 -->
    <c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="i">
    	<c:choose>
    		<c:when test="${paging.curPage eq i }">
		    	<li class="active"><a href="/board/list?curPage=${i }">${i }</a></li>
    		</c:when>
    		<c:otherwise>
		    	<li><a href="/board/list?curPage=${i }">${i }</a></li>
    		</c:otherwise>
    	</c:choose>
    </c:forEach>
<%--    	<c:if test="${paging.curPage ne paging.totalPage }"> --%>
<!-- 	    다음 페이지로 가기 -->
<!-- 	    <li> -->
<%-- 	      <a href="/board/list?curPage=${paging.curPage + 1 }" aria-label="Previous"> --%>
<!-- 	        <span aria-hidden="true">&raquo;</span> -->
<!-- 	      </a> -->
<!-- 	    </li> -->
<%--    	</c:if> --%>
    <!-- 다음 페이징 리스트 가기 -->
   	<c:if test="${paging.endPage ne paging.totalPage }">
	    <li>
	      <a href="/board/list?curPage=${paging.startPage + paging.pageCount }" aria-label="Previous">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
   	</c:if>
   	<c:if test="${paging.endPage eq paging.totalPage }">
	    <li>
	      <a href="#" aria-label="Previous">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
   	</c:if>
    <!-- 마지막으로 가기 -->
   	<c:if test="${paging.curPage ne paging.endPage }">
	    <li>
	      <a href="/board/list?curPage=${paging.endPage }" aria-label="Previous">
	        <span aria-hidden="true">&raquo;&raquo;</span>
	      </a>
	    </li>
   	</c:if>
  </ul>
</div>
  <div class="col-md-3 text-right" style="margin: 20px 0;">
	  <a class="btn btn-success" href="/board/write" role="button">글작성</a>
  </div>