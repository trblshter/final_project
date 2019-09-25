 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	.img {
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 5px;
		width: 100px;
	}
</style>

<script type="text/javascript">

	function ${pagingVO.funcName}(page){
		if(page<=0) return;
		teacherForm.find("input[name='page']").val(page);
		teacherForm.submit();
		teacherForm.find("input[name='page']").val("");
	}

	$(function(){
		var teacherForm = $('#teacherForm');
		var listBody = $("tbody#listBody");
		var pagingArea = $('#pagingArea');
		var userId = $('#userId').val();
		var userType = $('#userType').val();
		console.log(userType);
		
		teacherForm.on('submit', function(event){
			event.preventDefault();
			var queryString = $(this).serialize();
			var action = $(this).attr('action')
			if(userId != ""){
				if(userType == 'tutor'){
					$.ajax({
						url : action,
						data : queryString,
						dataType : 'json',
						method : 'post',
						success : function(resp){
							let teacherCommentList = resp.pagingVO.dataList;
							console.log(teacherCommentList);
							console.log(resp.message);
							var trTags = [];
							if(teacherCommentList && teacherCommentList.length > 0 && typeof resp.message == "undefined"){
								$(teacherCommentList).each(function(idx, teacherComment){
									var tr = $("<tr>").prop("id", teacherComment.lc_no);
									tr.append(
											$("<td style='text-align:center; vertical-align: middle;'>").html(
													teacherComment.user_id
											)		
		//										<img class="img" src="data:image/*;base64,${lectureTutor.lt_imageBase64}">
											, $("<td style='text-align:center; vertical-align: middle;'>").html(
													teacherComment.user_imageBase64 ? 
													$("<img>").prop({
														"class" : "img",
														"src"   : "data:image/*;base64," + teacherComment.user_imageBase64
													})		
													: ""
											)		
											, $("<td style='vertical-align: middle;'>").html(teacherComment.lc_content)
											, $("<td style='vertical-align: middle; text-align: center;'>").html(
													teacherComment.lc_date
											)		
											, $("<td style='vertical-align: middle; text-align: center;'>").html(
												$('<button>').prop({
														'class' : 'btn btn-primary btn-sm'
												})		
														     .text('선택하기')
											)
									);
									trTags.push(tr);
								});
							}else if(resp.message != ""){
								alert(resp.message);
								var trTags = [];
								if(teacherCommentList && teacherCommentList.length > 0){
									$(teacherCommentList).each(function(idx, teacherComment){
										var tr = $("<tr>").prop("id", teacherComment.lc_no);
										tr.append(
												$("<td style='text-align:center; vertical-align: middle;'>").html(
														teacherComment.user_id
												)		
		//											<img class="img" src="data:image/*;base64,${lectureTutor.lt_imageBase64}">
												, $("<td style='text-align:center; vertical-align: middle;'>").html(
														teacherComment.user_imageBase64 ? 
														$("<img>").prop({
															"class" : "img",
															"src"   : "data:image/*;base64," + teacherComment.user_imageBase64
														})		
														: ""
												)		
												, $("<td style='vertical-align: middle;'>").html(teacherComment.lc_content)
												, $("<td style='vertical-align: middle; text-align: center;'>").html(
														teacherComment.lc_date
												)		
												, $("<td style='vertical-align: middle; text-align: center;'>").html(
												$('<button>').prop({
														'class' : 'btn btn-primary btn-sm',
														'id'    : 'teacherSelectBtn'
												})		
														     .text('선택하기')
											)
										);
										trTags.push(tr);
									});
								}
							}
							teacherForm[0].reset();
							listBody.html(trTags);
							pagingArea.html(resp.pagingVO.pagingHTMLForBS);
						},
						error : function(){
							
						}
					})
				}else {
					alert("선생님만 등록 가능합니다");
					teacherForm[0].reset();
				}
			}else {
				alert('로그인 하셔야 합니다');
				teacherForm[0].reset();
			}
		})
		
		var user = $('#lu_writer').val();

		$('#teacherSelectForm').on('submit', function(event){
			event.preventDefault();
			var queryString = $(this).serialize();
			if(userId != ""){	
				if(userId == user){
					$.ajax({
						url : $(this).attr('action'),
						dataType : 'text',
						method : 'post',
						data : queryString,
						success : function(resp){
							alert(resp);
							
							location.href="${pageContext.request.contextPath }/lecture/lectureList.do";
						},
						error : function(){
							
						}
					})
				}else {
					alert('선택 못하십니다')
				}
			}else {
				alert('로그인 하셔야 합니다')
			}
		})
		
	});
</script>


<br>
<div style=" width : 50%; margin: auto;">
<i class="fas fa-angle-right 1g" style="">&nbsp;학생이 선생님 구할 때</i>
<!-- <button class="btn btn-warning btn-sm" style="float: right; width: 74px; height: 30px; margin-bottom: 5px;" disabled="disabled">모집중</button><br> -->
<form action='<c:url value="applInsert.do"/>' method="post" id="insertForm">
<table class="table table-striped" style="margin-top: 10px; margin-bottom: 5px;">
	<thead class="thead-light">
		<tr>
			<th style="width: 10%; text-align: center;">글제목</th>
			<th>
				${lectureUser.lu_title }
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th style="text-align: center;">작성자</th>
			<td >
				<input type="hidden" id="lu_writer" value="${lectureUser.lu_writer }"/>
				${lectureUser.lu_writer}
			</td>
		</tr>
		<tr>
<%-- 			<fmt:formatDate var="lu_date" value="${lectureUser.lu_date }" pattern="yyyy.mm.dd"/> --%>
			<th style="text-align: center;">작성날짜</th>
			<td>${lectureUser.lu_date }</td>
		</tr>
		<tr>
			<th style="text-align: center;">모집인원</th>
			<td>4</td>
		</tr>
		<tr>
			<th style="text-align: center;">내용</th>
			<td>${lectureUser.lu_content}</td>
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
	</tbody>
	</table>
	<input type="hidden" name="appl_user" value="${loginUser.user_id}" id="userId"/>
	<input type="hidden" name="user" value="${loginUser.user_type}" id="userType"/>
	<input type="hidden" name="lt_no" value="${lecturetutor.lt_no }"/>
	</form>
	</div>
	
<br>
<div style=" width : 50%; margin: auto;">
<i class="fas fa-angle-right 1g" style="">&nbsp;댓글입력</i>

<c:set var="reviewList" value="${review }" />
	<form action="<c:url value='/teacher/teacherCommentInsert.do'/>" method="post" id="teacherForm">
	<table class="table table-striped" style="margin-top: 10px;">
		<tr>
			<td style="text-align: center; vertical-align: middle;">
				<input type="hidden" name="page" />
				<input type="hidden" name="user_id" value="${loginUser.user_id }"/>
				<input type="hidden" name="lu_no" value="${lectureUser.lu_no }"/>
				<textarea rows="5" cols="15" class="form-control" style="margin-left: 4px;" name="lc_content"></textarea>
			</td>
			<td style=" width: 100px;">
				<input type="submit" value="등록" class="form-control" style="margin-left: -5px; height: 134px;" width="80px;"/>
			</td>
		</tr>
	</table>
	</form>
</div>

<div style=" width : 50%; margin: auto;">
<i class="fas fa-angle-right 1g" style="">&nbsp;선생님 정보</i>

<c:set var="reviewList" value="${review }" />


	<table class="table table-striped" style="margin-top: 10px;">
		<c:set var="categoryList" value="${lecturetutor.categoryList }"/>
		<thead class="thead-light">
			<tr>
				<th style="width: 10%; text-align: center;">이름</th>
				<th style="text-align: center; width: 20%;">이미지</th>
				<th style="text-align: center; width: 35%;">내용</th>
				<th style="text-align: center; width: 20%;">작성일</th>
				<th style="text-align: center; width: 10%;">비고</th>
			</tr>
		</thead>
		<tbody style="text-align: center;" id="listBody">
			<c:forEach var="teacherComment" items="${pagingVO.dataList }">
<%-- 			<fmt:formatDate var="lc_date" value="${teacherComment.lc_date }" pattern="yyyy.mm.dd"/>  --%>
				<tr>
					<td style="text-align: center; vertical-align: middle;">${teacherComment.user_id }</td>
					<td style="text-align: center; vertical-align: middle;">
						<img class="img" src="data:image/*;base64,${teacherComment.user_imageBase64}">
					</td>
					<td style="text-align: center; vertical-align: middle;">${teacherComment.lc_content }</td>
					<td style="text-align: center; vertical-align: middle;">${teacherComment.lc_date }</td>
					<td style="text-align: center; vertical-align: middle;">
						<form action="<c:url value='/teacher/teacherUpdate.do'/>" method="post" id="teacherSelectForm">
							<input type="hidden" name="lu_tutor" value="${teacherComment.user_id }"/>
							<input type="hidden" name="team_no" value="${lectureUser.team_no }"/>
							<input type="hidden" name="lu_writer" value="${lectureUser.lu_writer }"/>
							<input type="hidden" name="lu_no" value="${lectureUser.lu_no }"/>
							<input type="submit" class="btn btn-primary btn-sm" value="선택하기" 
								${lectureUser.lu_tutor ne null ? 'disabled' : '' } >
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="pagingArea" style="width: 950px; margin-left: 10px; display: inline-block;">${pagingVO.pagingHTMLForBS }</div>
</div>

