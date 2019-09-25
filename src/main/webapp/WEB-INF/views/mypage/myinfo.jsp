<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
th {
	width: 30%;
}

td {
	width: 70%;
}

img {
	max-width: 100%;
}

.imgTd {
	background: #f1f1f1;
}
.imgTd img {
    width: 300px;
    border:1px solid #ddd;
/*     border-radius: 50%; */
}
</style>
<h1>나의 정보</h1>
<c:if test="${not empty message }">
	<div class="alert alert-primary mt-4" role="alert">
	  ${message }
	</div>
</c:if>
<form method="post" 
	enctype="multipart/form-data">
<table class="table mt-4 w-100">
	<tr>
		<th scope="row">회원아이디</th>
		<td><input type="text" readonly class="form-control" required
			name="user_id" value="${loginUser.user_id}" /> <form:errors
				path="user_id" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">기존 비밀번호</th>
		<td><input type="password" class="form-control" required
			name="user_pass" /> <form:errors
				path="user_pass" element="span" cssClass="error" />
		</td>
	</tr>
	<tr>
		<th scope="row">신규 비밀번호</th>
		<td><input type="password" class="form-control" 
			name="new_pass" /> <form:errors
				path="user_pass" element="span" cssClass="error" />
		</td>
	</tr>
	<tr>
		<th scope="row">비밀번호 확인</th>
		<td><input type="password" class="form-control" 
			name="new_pass_chk" /> <form:errors
				path="user_pass" element="span" cssClass="error" />
		</td>
	</tr>
	<tr>
		<th scope="row">회원이름</th>
		<td><input type="text" class="form-control" required
			name="user_name" value="${loginUser.user_name}" /> <form:errors
				path="user_name" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원우편번호</th>
		<td><input type="text" class="form-control" required
			name="user_zipcode" value="${loginUser.user_zipcode}" /> <form:errors
				path="user_zipcode" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원주소</th>
		<td><input type="text" class="form-control" required
			name="user_addr1" value="${loginUser.user_addr1}" /> <form:errors
				path="user_addr1" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원상세주소</th>
		<td><input type="text" class="form-control" required
			name="user_addr2" value="${loginUser.user_addr2}" /> <form:errors
				path="user_addr2" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원이메일</th>
		<td><input type="text" class="form-control" required
			name="user_email" value="${loginUser.user_email}" /> <form:errors
				path="user_email" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원전화번호</th>
		<td><input type="text" class="form-control" required
			name="user_tel" value="${loginUser.user_tel}" /> <form:errors
				path="user_tel" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원생일</th>
		<td><input type="text" class="form-control" required
			name="user_birth" value="${loginUser.user_birth}" /> <form:errors
				path="user_birth" element="span" cssClass="error" /></td>
	</tr>
	<tr>
		<th scope="row">회원사진</th>
		<td class="imgTd">
			<c:if
				test="${not empty loginUser.user_imageBase64}">
				<input type="file" name="user_img" accept="image/*" class="form-control"/> <br /> 
				<img src="data:image/*;base64,${loginUser.user_imageBase64}" />
			</c:if> 
			<c:if test="${empty loginUser.user_imageBase64}">
				<input type="file" name="user_img" ${userType eq 'tutor'? 'required':''} accept="image/*" /><br />  
				등록된 이미지가 없습니다.
			</c:if> <form:errors path="user_image" element="span" cssClass="error" />
		</td>
	</tr>
<!-- 	<tr> -->
<!-- 		<th scope="row">계좌은행</th> -->
<!-- 		<td><input type="text" class="form-control" required -->
<%-- 			name="user_account_name" value="${loginUser.user_account_name}" /> <form:errors --%>
<%-- 				path="user_account_name" element="span" cssClass="error" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<th scope="row">계좌번호</th> -->
<!-- 		<td><input type="text" class="form-control" required -->
<%-- 			name="user_account_num" value="${loginUser.user_account_num}" /> <form:errors --%>
<%-- 				path="user_account_num" element="span" cssClass="error" /></td> --%>
<!-- 	</tr> -->
	<c:if test="${loginUser.user_type eq 'tutor' }">
		<tr>
			<th scope="row">경력</th>
			<td><input type="text" class="form-control" required
				name="tt_career" value="${loginUser.tt_career}" /> <form:errors
					path="tt_career" element="span" cssClass="error" /></td>
		</tr>
		<tr>
			<th scope="row">학력</th>
			<td><input type="text" class="form-control" required
				name="tt_edu" value="${loginUser.tt_edu}" /> <form:errors
					path="tt_edu" element="span" cssClass="error" /></td>
		</tr>
		<tr>
			<th scope="row">인증여부확인</th>
			<td>
				<div class="alert ${loginUser.tt_auth eq 0? 'alert-danger':'alert-success'} mb-0 p-2" role="alert">
				  ${loginUser.tt_auth eq 0? '미인증':'인증완료'}
				</div>
				<input type="hidden" readonly class="form-control" name="tt_auth" value="${loginUser.tt_auth }" />
			</td>
		</tr>
		<tr>
			<th scope="row">수료증첨부파일이미지</th>
			<td class="imgTd">	
				<c:if
					test="${not empty loginUser.tt_ctr_imageBase64}">
					<input type="file" name="tt_ctr_img" accept="image/*" class="form-control"/> <br />
					<img src="data:image/*;base64,${loginUser.tt_ctr_imageBase64}" />
				</c:if> <c:if test="${empty loginUser.tt_ctr_imageBase64}">
					<input type="file" required name="tt_ctr_img" accept="image/*" /><br /> 
					등록된 이미지가 없습니다.
				</c:if> <form:errors path="tt_ctr_image" element="span" cssClass="error" />
			</td>
		</tr>
	</c:if>
</table>

<button type="submit" class="btn btn-primary mb-5">회원 정보 수정</button>
</form>

