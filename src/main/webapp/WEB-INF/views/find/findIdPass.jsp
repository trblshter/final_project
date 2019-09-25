<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/helpSem/css/form-signin.css">
<div class="container">
<h1 class="text-center">회원 정보 찾기</h1>
<ul class="nav nav-tabs form-signin justify-content-center pb-0" id="myTab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="findId_tab" data-toggle="tab" href="#findIdForm" role="tab" aria-controls="findIdForm" aria-selected="true">아이디 찾기</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="findPass_tab" data-toggle="tab" href="#findPassForm" role="tab" aria-controls="findPassForm" aria-selected="false">비밀번호 찾기</a>
  </li>
</ul>
<div class="tab-content form-signin" id="myTabContent" >
  <div class="tab-pane fade show active" id="findIdForm" role="tabpanel" aria-labelledby="findId_tab">
  	<form id="findIdForm" method="post" action="<c:url value='/find/findId' />" data-formtype="findIdModal">
         <div class="inputWrap">
       	<div class="form-group mx-sm-3 mb-2">
			<label for="findName">이름</label>
			<input type="text" required class="form-control" id="findName" name="user_name">
		</div>
		<div class="form-group mx-sm-3 mb-2">
			<label for="findEmail">메일</label>
			<input type="email" required class="form-control" id="findEmail" name="user_email">
		</div>
         </div>
	  <button type="submit" class="btn btn-primary w-100 mt-4">확인</button>
	</form>
  </div>
  <div class="tab-pane fade" id="findPassForm" role="tabpanel" aria-labelledby="findPass_tab">
  	<form id="findPassForm" method="post" action="<c:url value='/find/findPass' />" data-formtype="findPassModal">
         <div class="inputWrap">
       	<div class="form-group mx-sm-3 mb-2">
			<label for="findId">아이디</label>
			<input type="text" required class="form-control" id="findId" name="user_id">
		</div>
		<div class="form-group mx-sm-3 mb-2">
			<label for="findEmail">메일</label>
			<input type="email" required class="form-control" id="findEmail" name="user_email">
		</div>
         </div>
	  <button type="submit" class="btn btn-primary w-100 mt-4">확인</button>
	</form>
  </div>
</div>
</div>

<!-- 인증번호 확인 Modal -->
<div class="modal fade" id="codeCheckModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">인증번호 확인</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="codeCheckForm">
        	<p>이메일로 입력받은 인증번호를 입력하세요.</p>
          <input type="text" required class="form-control" id="codeCheck">
		  <button type="button" class="btn btn-primary codeCheckBtn">확인</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
		</form>
		<div id="idView">
        	
		</div>
      </div>
    </div>
  </div>
</div>
<!-- 비밀번호 변경 Modal -->
<div class="modal fade" id="changePassModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">비밀번호 변경</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="changePass" method="post" action="<c:url value='/find/updatePass' />">
	        <input type="hidden" required name="user_id"> 
	        <label for="new_pass">새 비밀번호</label>
	        <input type="text" required class="form-control" id="new_pass" name="new_pass">
	        <label for="new_pass_chk">비밀번호 확인</label>
	        <input type="text" required class="form-control" id="new_pass_chk" name="new_pass_chk">
			  <button type="submit" class="btn btn-primary">확인</button>
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
		</form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
authCode = "";
codeCheckForm = $("#codeCheckForm");
codeCheckModal = $("#codeCheckModal");
changePassModal = $("#changePassModal");
idView = $("#idView");
formtype = "";
findUser = null;
$("#findIdForm, #findPassForm").on("submit", function(event){
	event.preventDefault();
	var url = $(this).attr("action");
	var method = $(this).attr("method");
	var data = $(this).serialize();
	var modal = $("#codeCheckModal");
	formtype = $(this).data("formtype");
	$.ajax({
		url : url,
		data : data,
		method : method,
		dataType : "json", // Accept, Content-Type
		success : function(resp){
			if(resp.result){
				modal.modal("show");
				authCode = resp.authCode;
				findUser = resp.findUser;
				alert("인증코드가 메일로 발송되었습니다.");
			}else{
				alert(resp.message);
			}
		},
		error : function(resp){
			console.log(resp.status);
		}
	});
	return false;
});

$(".codeCheckBtn").on("click", function(){
	var codeCheck = $("#codeCheck").val();
	if(authCode == codeCheck){
		var user_id = findUser.user_id;
		if("findIdModal" == formtype){
			var idViewStr = findUser.user_id.substring(1, 3);
			codeCheckForm.hide();
			idView.show().append(
					$("<p>").text("회원님의 아이디는"),
					$("<p>").text(findUser.user_id.replace(idViewStr, '**') + " 입니다."),
					$("<button type='button' class='btn btn-secondary' data-dismiss='modal'>확인</button>")
				);
		}else{
			codeCheckModal.modal("hide");
			changePassModal.modal("show");
			changePassModal.find("[name='user_id']").val(user_id);
		}
	}else{
		alert("인증코드가 틀렸습니다.");
	}
});

$(".modal").on("hide.bs.modal", function(){
	idView.hide();
	codeCheckForm[0].reset();
	codeCheckForm.show();
});
$(".modal").on("shown.bs.modal", function(){
	$(this).find('input')[0].focus();
});

$("#changePass").on("submit", function(event){
	event.preventDefault();
	var url = $(this).attr("action");
	var method = $(this).attr("method");
	var data = $(this).serialize();
	$.ajax({
		url : url,
		data : data,
		method : method,
		dataType : "json", // Accept, Content-Type
		success : function(resp){
			if(resp.result){
				alert(resp.message);
				changePassModal.modal("hide");
			}else{
				alert(resp.message);
			}
		},
		error : function(resp){
			console.log(resp.status);
		}
	});
	return false;
});
</script>