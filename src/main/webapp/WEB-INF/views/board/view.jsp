<%@page import="kr.or.ddit.utils.StrUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
.boardEditDiv{ display: none;}
.boadViewDiv{ }
.boardContent{ width:100%; min-height:200px; }
table{ width:100%; }
</style>

<div class="container">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script type="text/javascript" src="<c:url value='/js/ckeditor/ckeditor.js'/>"></script>
<script>
	$(function(){
		var editor = CKEDITOR.replace(
				'bo_content',
				{
					extraPlugins : 'image',
					filebrowserImageUploadUrl : "${uploadImageUrl}"
				});
		// 게시판 삭제 버튼 이벤트 처리 
		$("#btnDelete").click(function(){
			var caption = $(this).text();
			
			// 수정버튼을 누르면 '삭제'버튼이 '수정취소'버튼으로 바뀌는데 그것에 대한 처리
			if(caption=="수정취소"){
				$("#btnUpdate").text("수정");
				$("#btnDelete").text("삭제");
				document.form1.reset(); // HTML문서.form1.리셋
				$(".boardEditDiv").hide();
				$(".boadViewDiv").show();
				
				return false;
			}
			
			// 삭제 처리
			if(confirm("삭제하시겠습니까?")){
				document.form1.action = "${pageContext.request.contextPath }/board/delete.do";
				document.form1.submit(); // html문서 form1을 전송.
			}
		});
		
		// 게시판 수정 버튼 이벤트 처리
		$("#btnUpdate").click(function(){
			// var title = document.form1.title.value; ==> name속성으로 처리할 경우 
			// var content = document.form1.content.value;
			// var writer = document.form1.writer.value;
			var caption = $(this).text();
			
			// 수정 버튼을 클릭하면 '수정'버튼은 '수정등록'버튼으로, '삭제'버튼은 '수정취소'버튼으로 바뀌도록 처리하는 부분
			if(caption=="수정"){
				$("#btnUpdate").text("수정등록");
				$("#btnDelete").text("수정취소");
				$(".boardEditDiv").show();
				$(".boadViewDiv").hide();
				attachShowHide(); //첨부파일 영역 보이기 또는 감추기 처리 함수 호출
				return false;
			}
			
			// 수정 등록을 처리하는 부분
			var bo_title = $("#bo_title").val();
			var bo_content = $("#bo_content").val();
			var bo_writer = $("#bo_writer").val();
// 			bo_content = bo_content.substring(0, bo_content.lastIndexOf("<br>"));
			
			if(bo_title == ""){
				alert("제목을 입력하세요");
				document.form1.title.focus();
				return;
			}
			if(bo_content == ""){
				alert("내용을 입력하세요");
				document.form1.bo_content.focus();
				return;
			}
			if(bo_writer == ""){
				alert("이름을 입력하세요");
				document.form1.writer.focus();
				return;
			}
			document.form1.action="${pageContext.request.contextPath }/board/update.do"
			
			// 폼에 입력한 데이터를 서버로 전송
			document.form1.submit();
		});
	});
	
</script>	
<!-- <h2>게시글 보기</h2> -->

<form name="form1" method="post" id="boardForm" enctype="multipart/form-data" class="mt-5">
<div class="boadViewDiv">
	<h1 class="mb-3">${dto.bo_title }</h1>
	<div class="bg-light border-top border-bottom p-3">
	<span><strong>작성자</strong>  ${dto.bo_writer}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><strong>작성일자</strong> ${dto.bo_date}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><strong>조회수</strong> ${dto.bo_views}</span>
	</div>
	<div class="boardContent p-3">${dto.bo_content}</div>
	
</div>
<div class="boardEditDiv">
	<h1 class="mb-4">게시글 수정</h1>
	<input name="type" type="hidden" value="${type }">
	
	<table class="table mb-0">
	<tbody>
		<tr>
			<th>제목</th>
			<td colspan="3">
				<input class="form-control" name="bo_title" id="title" size="80" value="${dto.bo_title }" placeholder="제목을 입력해주세요.">
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="3">
				<textarea name="bo_content" id="bo_content" rows="4" cols="80" placeholder="내용을 입력해주세요.">${dto.bo_content}</textarea>
				<!-- 엔터 적용하기 -->
<%-- 				<c:set var="newLine" value="<%='\\n' %>"/> --%>
<%-- 				<div class="boadViewDiv boardContent">${fn:replace(dto.bo_content, newLine, '<br>')}</div>				 --%>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td colspan="3">
				<input name="bo_writer" type="hidden" value="${dto.bo_writer}" placeholder="이름을 입력해주세요.">
				${dto.bo_writer}
			</td>
		</tr>
<%-- 		<c:if test="${dto.attachList.size() lt 3}"> --%>
		<tr id="attachFilesArea">
			<th>첨부파일</th>
			<td><input type="file" name="bo_files"></td>
		</tr>
<%-- 		</c:if> --%>
	</tbody>
	</table>
	
	<br><br>
	
</div>
<c:if test="${not empty dto.attachList }">
	<div class="alert alert-info" role="alert">
		<span class="tdHead mr-4"><strong>첨부파일</strong></span> 
			<c:forEach var="attach" items="${dto.attachList}" varStatus="vs">
						
				<c:url value='/download.do' var="downloadURL">
					<c:param name="what" value="${attach.att_no }"/>
				</c:url>
				<c:set var="downTitle" value="크기:${attach.att_fancysize }"/>
				<span class="attachSpan">
					<a href="${downloadURL }" title="${downTitle }">${attach.att_filename }</a>
					<button class="delfilebtn boardEditDiv" type="button" data-attno="${attach.att_no }">x</button>
				</span>
				${not vs.last ? "|" : "" }
				
			</c:forEach>
	</div>
	</c:if>
<!-- 게시물 번호를 hidden으로 처리 -->		
		<input type="hidden" name="bo_no" value="${dto.bo_no }">
		<c:if test="${dto.bo_writer==sessionScope.loginUser.user_id || sessionScope.loginUser.user_auth.toString() == 'A'.toString()}">
			<div class="text-right">
			<button type="button" id="btnUpdate" class="btn btn-primary">수정</button>&nbsp;&nbsp;
			<button type="button" id="btnDelete" class="btn btn-danger">삭제</button>
			</div>
		</c:if>
</form>
	<hr color="black">
<!-- 	===================================================================== -->
	<div class="boadViewDiv">
		<!-- 댓글 작성 textArea과 등록 버튼 -->
		<form method="post" id="insertCommentForm" action="<c:url value='/comment/commentInsert.do'/>">
			<div class="input-group">
	  			<div class="input-group-prepend">
	    			<span class="input-group-text">With textarea</span>
	  			</div>
	  			<input type="hidden" name="bo_no" value="${dto.bo_no }">
	  			<input type="hidden" name="cm_writer" value="${sessionScope.loginUser.user_id }">
	  			<textarea class="form-control" name="cm_content" aria-label="With textarea"></textarea>&nbsp;&nbsp;&nbsp;
	  			<input type="button" id="cmtAddBtn" value="댓글쓰기" class="btn btn-info" />
			</div>
		</form>
	</div>
	
	
	<div id="commentContainer" class="boadViewDiv"> 
	<h4 class="mt-4">댓글 목록</h4>
	<hr>
	<c:forEach var="cmt" items="${comment}">
	<div class="reWrap row">
		<div class="imgWrap col-2">
		</div>
		<div id="cmt${cmt.cm_no }" class="col-10">
			<div class="cmWriter">${cmt.cm_writer}</div>
			
			<c:set var="newLine" value="<%='\\n' %>"/>
			<div class="cmContent">${fn:replace(cmt.cm_content, newLine, '<br>')}</div>
			
			<!-- 답글 버튼 -->
			
			<!-- 수정 작업할 공간 만들기 -->	
			<c:if test="${cmt.cm_writer==sessionScope.loginUser.user_id }">
				<input type="button" value="수정" class="btn btn-info cmtEditBtn" />			
			</c:if>
			<!-- 삭제 -->
			<c:if test="${cmt.cm_writer==sessionScope.loginUser.user_id || sessionScope.loginUser.user_auth.toString() == 'A'.toString()}">
				<input type="button" value="삭제" class="btn btn-info cmtDelBtn"/>
			</c:if>	
			<br><br>
		</div>
		</div>
		<hr>
	</c:forEach>
	</div>
	
	
	<!-- 댓글 수정 폼 영역 -->
	<div id="commentEditFormDiv" style="display:none;">
		<form id="cmtEditForm">
			<div class="input-group">
	  			<div class="input-group-prepend">
	    			<span class="input-group-text">내용 수정</span>
	  			</div>
	  			<input type="hidden" name="cm_no">
				<input type="hidden" name="cm_writer" value="${sessionScope.loginUser.user_id }">
	  			<textarea class="form-control" name="cm_content" aria-label="내용 수정"></textarea>&nbsp;&nbsp;&nbsp;
	  			<input type="button" id="cmtEditBtn" value="등록" class="btn btn-info" />&nbsp;&nbsp;
	  			<input type="button" id="cmtCancelBtn" value="취소" class="btn btn-info" />
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
	// 첨부파일 영역 보이기 또는 감추기 처리 함수
	function attachShowHide(){
		console.log($(".attachSpan").length);
		var attachSpanLen = $(".attachSpan").length;
		
		if(attachSpanLen<3){
			$("#attachFilesArea").show();
		}else{
			$("#attachFilesArea").hide();
		}
	}
	
	
	var boardForm = $("#boardForm");
	// 삭제할 첨부파일들에 대한 정보를 서버로 전송하기 위해 동적 UI 추가
	var delFileInput = "<input type='hidden' name='delFiles' value='%V' />";
	$(".delfilebtn").on("click", function() {
		var att_no = $(this).data("attno");
		boardForm.prepend(delFileInput.replace("%V", att_no));
		console.log(att_no);
		$(this).closest("span").remove();
		
		attachShowHide();  // 첨부파일 영역 보이기 또는 감추기 처리 함수 호출
	});
	
	
	//-----------------------------------------------
		// 댓글 수정 취소버튼 클릭 이벤트
		$("#commentEditFormDiv").on("click", "#cmtCancelBtn", function(){
			$("body").append($("#commentEditFormDiv"));
			$("#commentEditFormDiv").hide();
		});
		
		// 댓글 수정 '등록' 버튼 클릭 이벤트
		$("#commentEditFormDiv").on("click", "#cmtEditBtn", function(){
			var $cmtEditForm = $("#cmtEditForm");
			var $parentDiv = $("#commentEditFormDiv").parent();
			
			var insertData = $cmtEditForm.serialize();
			var cmContent = $("textarea", $cmtEditForm).val();
			
			$.ajax({
				url : "<c:url value='/comment/commentUpdate.do'/>",
				data : insertData,
				dataType : "json",
				type : "post",
				success : function(res){
					if(res.success==true){
						
						$(".cmContent", $parentDiv).html(cmContent.replace(/\n/g, "<br>"));
// 						$(".cmContent", $parentDiv).html(cmContent);
						// 수정 등록 성공후 '수정 작업 영역' 감추기
						$("body").append($("#commentEditFormDiv")); // body가. 끝나고(commentEditFormDiv)에 대해
						$("#commentEditFormDiv").hide(); // commentEditFormDiv를.숨김();
						alert("댓글 수정 성공");
						location.href="";
					}else{
						alert("댓글 수정 실패 :: " + res.message);	
					}
				}				
			});
		});
	
		// 댓글 '수정' 버튼 클릭 이벤트
		$("#commentContainer").on("click", ".cmtEditBtn", function(){
						
			$parentDiv = $(this).parent();
			var cmNo = $parentDiv.attr("id").substring(3);
			var cmContent = $(".cmContent", $parentDiv).html().trim();
			//alert(cmContent);
			//alert(cmContent.replace(/<br>/g, "\n"));
			
			$("#cmtEditForm input[name=cm_no]").val(cmNo);
			$("#cmtEditForm textarea").val(cmContent.replace(/<br>/g, "")); // br 지움!
			
			$parentDiv.append($("#commentEditFormDiv"));
			$("#commentEditFormDiv").show();
		});
	
		// 댓글 '삭제' 버튼 클릭 이벤트
		$("#commentContainer").on("click", ".cmtDelBtn", function(){
			$parentDiv = $(this).parent();
			var cmNo = $parentDiv.attr("id").substring(3);
			var boNo = $("input[name=bo_no]", $("form[name=form1]")).val();
			//alert(cmNo + ", " + boNo)
			
			if(!confirm("정말로 삭제하시겠습니까?")){
				return false;
			}
			
 			$.ajax({
 				url : "<c:url value='/comment/commentDelete.do'/>",
 				data : { "cmNo" : cmNo, "boNo" : boNo},
 				type : "post",
 				success : function(res){
 					if(res.success==true){
 						// 삭제 성공 후 다시 가져온 댓글 리스트를 출력한다. 
						var commentList = res.commentList;
						//alert(commentList.length);
						var htmlCode = "<h4 class='mt-4'>댓글 목록</h4><hr>";
						$.each(commentList, function(i, cmt){ // jQuery 유틸리티 메서드. // 배열관리
							htmlCode += '<div class="reWrap row">';
							htmlCode += '<div class="imgWrap col-2">';
							htmlCode += '</div>';
							htmlCode += '<div id="cmt' + cmt.cm_no + '" class="col-10">';
							htmlCode += '<div class="cmWriter">' + cmt.cm_writer + '</div>';
							htmlCode += '<div class="cmContent">' + cmt.cm_content.replace(/\n/g, "<br>") + '</div>';
// 							htmlCode += '<div class="cmContent">' + cmt.cm_content + '</div>';
							
							//===================
							if(cmt.cm_writer=="${sessionScope.loginUser.user_id }"){
								htmlCode += '<input type="button" value="수정" class="btn btn-info cmtEditBtn"/> ';
							}
							//====================
								
							if(cmt.cm_writer=="${sessionScope.loginUser.user_id}" || "${sessionScope.loginUser.user_auth}" == "A"){
								htmlCode += '<input type="button" value="삭제" class="btn btn-info cmtDelBtn"/>';
							}		
							htmlCode += '<br><br></div></div>';
							htmlCode += '<hr>';
						});
						
						$("#commentContainer").html(htmlCode);
						
					}else{
						alert("오류 ::: " + res.message);	
					}
 				},
 				dataType : "json"
 			});
			return false;
		});
		
		// '댓글쓰기' 버튼 클릭 이벤트
		var insertCommentForm = $("#insertCommentForm");
		//insertCommentForm.on("submit", function(event){
		$("#cmtAddBtn").on("click", function(){
			var $textArea = $("[name='cm_content']", insertCommentForm) 
			
			if("${sessionScope.loginUser.user_id}"==""){
				alert("로그인 후에 사용하세요.");
				$textArea.val('');
				return false;
			}
			var content = $textArea.val();
			if(content==""){
				alert("댓글 내용을 입력하세요.");
				return false;
			}
			
			var insertData = insertCommentForm.serialize();
			$.ajax({
				url : insertCommentForm.attr("action"),
				data : insertData,
				dataType : "json",
				type : "post",
				success : function(res){
					if(res.success==true){
						// 추가 성공 후 다시 가져온 댓글 리스트를 출력한다. 
						var commentList = res.commentList;
						//alert(commentList.length);
						var htmlCode = "<h4 class='mt-4'>댓글 목록</h4><hr>";
						$.each(commentList, function(i, cmt){
							htmlCode += '<div class="reWrap row">';
							htmlCode += '<div class="imgWrap col-2">';
							htmlCode += '</div>';
							htmlCode += '<div id="cmt' + cmt.cm_no + '" class="col-10">';
							htmlCode += '<div class="cmWriter">' + cmt.cm_writer + '</div>';
							htmlCode += '<div class="cmContent">' + cmt.cm_content.replace(/\n/g, "<br>") + '</div>';
// 							htmlCode += '<div class="cmContent">' + cmt.cm_content + '</div>';
							
							//===================
							if(cmt.cm_writer=="${sessionScope.loginUser.user_id }"){
								htmlCode += '<input type="button" value="수정" class="btn btn-info cmtEditBtn"/> ';
							}
							//====================
								
							if(cmt.cm_writer=="${sessionScope.loginUser.user_id}" || "${sessionScope.loginUser.user_auth}" == "A"){
								htmlCode += '<input type="button" value="삭제" class="btn btn-info cmtDelBtn"/>';
							}		
							htmlCode += '<br><br></div></div>';
							htmlCode += '<hr>';
						});
						
						$("#commentContainer").html(htmlCode);
						$textArea.val("");
						
					}else{
						alert("오류 ::: " + res.message);	
					}
				},
// 				여기 에러는 없어도 됨
				error : function(error){
					
				}
			});
		});
	</script>
</div>