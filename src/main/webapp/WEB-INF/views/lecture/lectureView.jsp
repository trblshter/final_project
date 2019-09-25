 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<br>
<div style=" width : 50%; margin: auto;">
<i class="fas fa-angle-right 1g" style="">&nbsp;선생님이 학생 구할 때</i>
<button class="btn btn-warning btn-sm" style="float: right; width: 74px; height: 30px; margin-bottom: 5px;" disabled="disabled">모집중</button><br>
<form action='<c:url value="applInsert.do"/>' method="post" id="insertForm">
<table class="table table-striped" style="margin-top: 10px; margin-bottom: 5px;">
	<c:set var="categoryList" value="${lecturetutor.categoryList }"/>
	<thead class="thead-light">
		<tr>
			<th style="width: 10%; text-align: center;">과목</th>
			<th>
				<c:forEach var="category" items="${categoryList }">
					${category.ctgy_name }				
				</c:forEach>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th style="text-align: center;">등록날짜</th>
			<td>${lecturetutor.lt_date }</td>
		</tr>
		<tr>
			<th style="text-align: center;">모집인원</th>
			<td>${lecturetutor.lt_recruit }</td>
		</tr>
		<tr>
			<th style="text-align: center;">제목</th>
			<td>${lecturetutor.lt_title}</td>
		</tr>
		<tr>
			<th style="text-align: center;">가격</th>
			<td>${lecturetutor.lt_price}</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<th>첨부파일</th> -->
<!-- 			<td> -->
<%-- 				<c:forEach var="attatch" items="${board.attatchList }"> --%>
<%-- 					<c:url value="/board/download.do" var="downloadURL"> --%>
<%-- 						<c:param name="what" value="${attatch.att_no }"></c:param> --%>
<%-- 					</c:url> --%>
<%-- 					<c:set var="downTitle" value="크기:${attatch.att_filesize },다운수:${attatch.att_download }"/> --%>
<%-- 					<a href="${downloadURL }" title="${downTitle }">${attatch.att_filename }</a> --%>
<%-- 				</c:forEach> --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr>
			<th style="text-align: center;">내용</th>
			<td>${lecturetutor.lt_content}</td>
		</tr>
	</tbody>
	</table>
	<input type="hidden" name="appl_user" value="${loginUser.user_id}" id="userId"/>
	<input type="hidden" name="lt_no" value="${lecturetutor.lt_no }"/>
	<input type="submit" class="btn btn-warning btn-sm" style="float: right; width: 74px; height: 30px;" id="addBtn" value="신청하기"/>
	</form>
	</div>
	
<br><br><br>
<div style=" width : 50%; margin: auto;">
<i class="fas fa-angle-right 1g" style="">&nbsp;리뷰</i>

<c:set var="reviewList" value="${review }" />


<table class="table table-striped" style="margin-top: 10px;">
	<c:set var="categoryList" value="${lecturetutor.categoryList }"/>
	<thead class="thead-light">
		<tr>
			<th style="width: 10%; text-align: center;">이름</th>
			<th style="text-align: center; width: 50%;">내용</th>
			<th style="text-align: center;">작성일</th>
			<th style="text-align: center;">별점</th>
		</tr>
	</thead>
	<tbody style="text-align: center;">
		<c:forEach var="review" items="${reviewList }">
			<tr>
				<td>${review.rv_writer }</td>
				<td>${review.rv_content }</td>
				<td>${review.rv_date }</td>
				<td>
					<c:forEach begin="1" end="${review.rv_grade }">
						<img src="${pageContext.request.contextPath }/image/star.png" style="width: 15px; height: 15px;">
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	
	
<script type="text/javascript">
	var insertForm = $('#insertForm');
	var userId = $('#userId').val();
	var userType = '${loginUser.user_type}';
	
	insertForm.on('submit', function(event){
		event.preventDefault();
		var data = $(this).serialize();
		console.log(userId);
		if(userId == ""){
			alert("로그인 해라");
		}else {
			if(userType == "tutee"){
				$.ajax({
					url : "${pageContext.request.contextPath }/appl/applInsert.do",
					data : data,
					method : "post",
					dataType : "text",
					success : function(resp){
						alert(resp);
						location.href="${pageContext.request.contextPath }/lecture/lectureList.do";
					},
					error : function(errorResp){
						
					} 
				});
			}else {
				alert("학생만 신청 가능합니다")
			}
		}
		return false;
	});
	
	
</script>