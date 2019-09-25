<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	.card-deck {margin:150px 0; padding:10%;}
</style>
<div class="container">
	<div class="card-deck">
	  <div class="card">
	    <div class="card-body text-center p-5">
	    <i class="fas fa-user-graduate display-3 mb-4"></i>
	      <h5 class="card-title font-weight-bold">학생으로 가입하기</h5>
	      <p class="card-text">학생회원으로 가입해서 선생님들이 개설한 수업을 이용하세요.</p>
	      <a href="<c:url value="/join/joinTutee.do" />" role="button" class="btn btn-primary w-100 mb-2">회원가입</a>
	    </div>
	  </div>
	  <div class="card">
	    <div class="card-body text-center p-5">
	    <i class="fas fa-chalkboard-teacher display-3 mb-4"></i>
	      <h5 class="card-title font-weight-bold">선생님으로 가입하기</h5>
	      <p class="card-text">선생님회원은 강의를 개설하고 실시간 강의로 학생들을 지도할 수 있습니다.</p>
	      <a href="<c:url value="/join/joinTutor.do" />" role="button" class="btn btn-primary w-100 mb-2">회원가입</a>
	    </div>
	  </div>
	</div>
</div>