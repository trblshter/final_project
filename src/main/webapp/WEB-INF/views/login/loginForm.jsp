<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/form-signin.css" />
<div class="container">
<form class="form-signin" action="<c:url value="/login/login.do" />" method="post">
  <div class="text-center mb-4">
    <h1 class="h3 mb-3 font-weight-bold">로그인</h1>
  </div>
  
<c:if test="${not empty message }">
	<div class="alert alert-primary mt-4" role="alert">
	  ${message }
	</div>
	<c:remove var="message" scope="session"/>
</c:if>
<c:set var="idCookieValue" value="${cookie.idCookie.value }"/>
  <div class="form-label-group">
    <input type="text" id="inputId" value="${idCookieValue }" class="form-control" placeholder="id" name="user_id" required autofocus>
    <label for="inputId">id</label>
  </div>

  <div class="form-label-group">
    <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="user_pass" required>
    <label for="inputPassword">Password</label>
  </div>

  <div class="checkbox mb-3">
    <label>
		<input type="checkbox"  name="cookieSaveId"  value="idSave"
			${not empty idCookieValue?"checked":"" } 
		 /> 아이디기억하기
    </label>
  </div>
  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  <hr>
  <a class="btn btn-warning btn-lg btn-block" href="https://kauth.kakao.com/oauth/authorize?client_id=f377b4843432beed4bbe37489851dc69&redirect_uri=http://localhost/helpSem/login/kakao&response_type=code">카카오로 로그인</a>
  <a class="btn btn-success btn-lg btn-block" href="https://nid.naver.com/oauth2.0/authorize?client_id=dE_jBxcFPFiJMquDx8sH&redirect_uri=http://localhost/helpSem/login/naver&response_type=code">네이버로 로그인</a>
  <hr>
  <a class="btn btn-secondary btn-lg btn-block" href="<c:url value="/join" />">회원가입</a>
  <p class="text-center mt-4">
	  <a href="<c:url value="/find" />" class="text-secondary" >아이디 / 비밀번호 찾기</a>
  </p>
  <p class="mt-5 mb-3 text-muted text-center">© 2017-2019</p>
</form>
</div>


