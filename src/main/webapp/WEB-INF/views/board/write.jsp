<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script type="text/javascript" src="<c:url value='/js/ckeditor/ckeditor.js'/>"></script>
<!-- ---- -->
<script>
	$(document).ready(function(){
		var editor = CKEDITOR.replace(
				'bo_content',
				{
					extraPlugins : 'image',
					filebrowserImageUploadUrl : "${uploadImageUrl}"
				});
		
		$("#btnSave").click(function(){
			//var title = document.form1.title.value; == name속성으로 처리할 경우
			//var content = document.form1.content.value;
			//var writer = document.form1.writer.value;
			var title = $("#bo_title").val(); // id
			var content = CKEDITOR.instances.bo_content.getData(); //$("#bo_content").val();
			
			var writer = $("#bo_writer").val();
			if(title == ""){
				alert("제목을 입력하세요");
				document.form1.title.focus();
				return false;
			}
			if(content == ""){
				alert("내용을 입력하세요");
				document.form1.content.focus();
				return;
			}
			if(writer == ""){
				alert("이름을 입력하세요");
				document.form1.writer.focus();
				return;
			}
			// 폼에 입력한 데이터를 서버로 전송
			document.form1.submit();
		});
	});
</script>

<div class="container" style="margin-top:50px;">
<h1 class="mb-4">게시글 작성</h1>
<!-- form은 입력을 받는 역활을 만든다.    enctye = 전송되는 데이터 형식./ multipart/form-data = 파일, 사진 전송 -->
<%-- <form name="form1" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath }/board/insert.do"> --%>
<form:form name="form1" method="post" id="boardForm" enctype="multipart/form-data" action="${pageContext.request.contextPath }/board/insert.do">
	<input name="type" type="hidden" value="${type }">
	
<!-- 	<div> -->
<!-- 		제목 -->
<!-- 		<input name="bo_title" id="bo_title" size="80" placeholder="제목을 입력해주세요."> -->
<!-- 	</div> -->
<!-- 	<div> -->
<!-- 		내용 -->
<!-- 		<textarea name="bo_content" id="bo_content" rows="4" cols="80" placeholder="내용을 입력해주세요."></textarea> -->
<!-- 	</div> -->
<!-- 	<div> -->
<!-- 		이름 -->
<%-- 		<input type="text" name="bo_writer" id="bo_writer" readonly value="${sessionScope.loginUser.user_id }"> --%>
<!-- 	</div> -->
<!-- 	<div> -->
<%-- 		<c:if test="${not empty board.attachjList }"> --%>
<!-- 			기존 파일 -->
<%-- 			<c:forEach var="attatch" items="${board.attatchList}" varStatus="vs">		 --%>
<%-- 				<c:url value='/board/download.do' var="downloadURL"> --%>
<%-- 					<c:param name="what" value="${attatch.att_no }"/> --%>
<%-- 				</c:url> --%>
<%-- 				<c:set var="downTitle" value="크기:${attach.att_fancysize }, 다운수 : ${attach.att_download }"/> --%>
<%-- 				<a href="${downloadURL }" title="${downTitle }">${attach.att_filename }</a> --%>
<%-- 				${not vs.last?"|":"" } --%>
<%-- 			</c:forEach> --%>
<%-- 		</c:if> --%>
<!-- 		파일 첨부 -->
<!-- 		<input type="file" name="bo_files"> -->
<!-- 	</div> -->
<!-- 	<div style="width:650px; text-align: center;"> -->
<!-- 		<button type="button" id="btnSave">확인</button>완료 -->
<!-- 		<button type="button" onclick="history.go(-1)">취소</button> -->
<!-- 	</div> -->
	<!-- ============================================================================== -->
	
	<div class="form-group row">
		<label for="bo_writer" class="col-sm-2 col-form-label">작성자</label><!-- 이름 -->
		<div class="col-sm-10">
			<input type="text" name="bo_writer" id="bo_writer" class="form-control" readonly value="${sessionScope.loginUser.user_id }">
		</div>
	</div>
	<div class="form-group row">
		<label for="bo_title" class="col-sm-2 col-form-label">제목</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" style="width: 100%;" name="bo_title" id="bo_title" placeholder="제목을 입력해주세요.">
		</div>
	</div>
	<div class="form-group row">
		<label for="bo_content" class="col-sm-2 col-form-label">내용</label>
		<div class="col-sm-10">
			<textarea name="bo_content" id="bo_content" class="form-control" rows="5" placeholder="내용을 입력해주세요."></textarea>
		</div>
	</div>	
	<div class="form-group row">
		<c:if test="${not empty board.attachjList }">
			
			기존 파일
			<c:forEach var="attatch" items="${board.attatchList}" varStatus="vs">		
				<c:url value='/board/download.do' var="downloadURL">
					<c:param name="what" value="${attatch.att_no }"/>
				</c:url>
				<c:set var="downTitle" value="크기:${attach.att_fancysize }, 다운수 : ${attach.att_download }"/>
				<span>
					<a href="${downloadURL }" title="${downTitle }">${attach.att_filename }</a>
					<button class="delFileBtn" type="button" data-attno="${attach.att_no }">X</button>
				</span>
				${not vs.last?"|":"" }
			</c:forEach>
			
		</c:if>
		<label for="inputContent3" class="col-sm-2 col-form-label">파일 첨부</label>
		<input type="file" name="bo_files">
	</div>
	<div style="width:650px; text-align: center;">
		<button type="button" id="btnSave" class="btn btn-primary">확인</button><!-- 완료 -->
		<button type="button" onclick="history.go(-1)" class="btn btn-primary">취소</button>
	</div>
</form:form>
</div>
<c:url var="uploadImageUrl" value='/board/uploadImage.do'>
	<c:param name="type" value="Images"></c:param>
</c:url>


<script type="text/javascript">
	var boardForm = $("#boardForm");
	// 삭제할 첨부파일들에 대한 정보를 서버로 전송하기 위해 동적 UI 추가
	var delFileInput = "<input type='text' name='delFiles' value='%V' />";
	$(".delFileBtn").on("click", function() {
		var att_no = $(this).data("attno");
		boardForm.prepend(delFileInput.replace("%V", att_no));
		console.log(att_no);
		$(this).closest("span").remove();
	});
	
	$(function () {
		$('#datetimepicker1').datetimepicker();
	});
</script>