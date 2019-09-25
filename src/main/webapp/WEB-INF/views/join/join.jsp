<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/form-signin.css" />
<div class="container">
	<h1 class="mb-3 text-center">
		<c:if test="${userType eq 'tutee'}">
		학생 
		</c:if>
		<c:if test="${userType eq 'tutor'}">
		선생님 
		</c:if>
		회원
	</h1>
	<form:form method="post" class="form-signin" commandName="user"
		enctype="multipart/form-data" id="userForm">
		<c:choose>
			<c:when test="${empty socialUser}">
				<div class="form-group" id="idBox">
					<label for="user_id">회원아이디</label> <input type="text" required
						name="user_id" value="${user.user_id }" class="form-control"
						id="user_id" />
					<button type="button" class="btn btn-outline-dark w-100 mt-2"
						id="idCheckBtn">아이디 중복 검사</button>
					<form:errors path="user_id" element="span" cssClass="error" />
				</div>
				<div class="form-group" id="passBox">
					<label for="user_pass">회원비밀번호</label><input type="password"
						required name="user_pass" 
						class="form-control" id="user_pass" />
					<form:errors path="user_pass" element="span" cssClass="error" />
				</div>
			</c:when>
			<c:otherwise>
				<input type="hidden" required name="user_id"
					value="${socialUser.user_id }" />
				<input type="hidden" required name="user_pass"
					value="${socialUser.user_pass }" />
				<c:remove var="socialUser" scope="session" />
			</c:otherwise>
		</c:choose>
		<div class="form-group">
			<label for="user_name">회원이름</label><input type="text" required
				name="user_name" value="${user.user_name}" class="form-control"
				id="user_name" />
			<form:errors path="user_name" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_zipcode">회원우편번호</label><input type="text" required
				name="user_zipcode" value="${user.user_zipcode}"
				class="form-control" id="user_zipcode" />
			<input type="button" id="searchZipCode" class="btn btn-secondary" value="찾기" />
			<form:errors path="user_zipcode" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_addr1">회원주소</label><input type="text" required
				name="user_addr1" value="${user.user_addr1}" class="form-control"
				id="user_addr1" />
			<form:errors path="user_addr1" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_addr2">회원상세주소</label><input type="text" required
				name="user_addr2" value="${user.user_addr2}" class="form-control"
				id="user_addr2" />
			<form:errors path="user_addr2" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_email">회원이메일</label><input type="text" required
				name="user_email" value="${socialUser.user_email}${user.user_email}"
				class="form-control" id="user_email" />
			<form:errors path="user_email" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_tel">회원전화번호</label><input type="text" required
				name="user_tel" value="${user.user_tel}" class="form-control"
				id="user_tel" />
			<form:errors path="user_tel" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_birth">회원생일</label><input type="date" required
				name="user_birth" value="${user.user_birth}" class="form-control"
				id="user_birth" />
			<form:errors path="user_birth" element="span" cssClass="error" />
		</div>
		<div class="form-group">
			<label for="user_image">회원사진</label><input type="file" ${userType eq 'tutor'? 'required':''}
				name="user_img" accept="image/*" class="form-control"
				id="user_image" />
			<form:errors path="user_image" element="span" cssClass="error" />
		</div>
<!-- 		<div class="form-group"> -->
<!-- 			<label for="user_account_name">계좌은행</label><input type="text" -->
<%-- 				required name="user_account_name" value="${user.user_account_name}" --%>
<!-- 				class="form-control" id="user_account_name" /> -->
<%-- 			<form:errors path="user_account_name" element="span" cssClass="error" /> --%>
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label for="user_account_num">계좌번호</label><input type="text" required -->
<%-- 				name="user_account_num" value="${user.user_account_num}" --%>
<!-- 				class="form-control" id="user_account_num" /> -->
<%-- 			<form:errors path="user_account_num" element="span" cssClass="error" /> --%>
<!-- 		</div> -->
		<c:if test="${userType eq 'tutor'}">
		<div id="inputTutor">
			<h2>선생님 추가 정보</h2>
			<input type="hidden" name="tutorCheck" value="true">
			<div class="form-group">
				<label for="tt_career">경력</label><input type="text"
					required name="tt_career" value="${user.tt_career}"
					class="form-control" id="tt_career" />
				<form:errors path="tt_career" element="span" cssClass="error" />
			</div>
			<div class="form-group">
				<label for="tt_edu">학력</label><input type="text"
					required name="tt_edu" value="${user.tt_edu}"
					class="form-control" id="tt_edu" />
				<form:errors path="tt_edu" element="span" cssClass="error" />
			</div>
			<div class="form-group">
				<label for="tt_ctr_image">수료증첨부파일이미지</label>
				<input type="file"
					required name="tt_ctr_img" accept="image/*" class="form-control"
					id="tt_ctr_image" />
				<form:errors path="tt_ctr_image" element="span" cssClass="error" />
			</div>	
		</div>
		</c:if>
		
		<div class="form-group" id="naverCaptcha">
			<label for="joinCaptcha">자동입력 방지문자</label>
			<div class="row mb-2">
				<div id="captchaImg" class="col-8"></div>
				<button type="button" class="btn text-primary" onclick="nCaptcha()">
					<i class="fas fa-redo-alt"></i> 새로고침
				</button>
				<div class="w-100 p-3">
					<input type="text" required name="userCaptchaStr" class="form-control"
						id="userCaptchaStr" /> <input type="button" value="인증"
						class="btn btn-secondary w-100 mt-2" onclick="nCaptchaResult()" />
				</div>
			</div>
		</div>

		<button type="submit" class="btn btn-primary btn-lg w-100 mt-4">회원가입</button>
	</form:form>
</div>
<script type="text/javascript">
	var userForm = $("#userForm");

	// 아이디 체크가 되어야만 submit 가능
	userForm.on("submit", function() {
		<c:if test="${empty socialUser}">
			$(this).data("idChecked", "true");
		</c:if>
		var idCheck = $(this).data("idChecked");
		
		var captchaCheck = $(this).data("captchaChecked");
		var chk = true;
		var msg = [];
		if (idCheck != "true") {
			msg.push("아이디");
			chk = false;
		}
		if (captchaCheck != "true") {
			msg.push("캡차");
			chk = false;
		}
		if (!chk) {
			alert(msg + " 를 체크해 주세요");
		}
		return chk;
	});

	// 아아디 중복 체크 
	$("#idCheckBtn").on("click", function() {
		var inputId = $("input[name=user_id]").val();
		var idBox = $("#idBox");
		var idMsg = $("#idMsg");
		$.ajax({
			url : "${pageContext.request.contextPath}/user/idCheck.do",
			data : {
				inputId : inputId
			},
			dataType : "json",
			method : "post",
			success : function(resp) {
				var message = null;
				if (resp.success && !resp.duplicated) {
					userForm.data("idChecked", "true");
					message = "사용 가능한 아이디.";
					msgClass = "text-success";
					idBox.find("input").prop('readonly', true);
				} else {
					message = "사용할 수 없는 아이디 입니다.";
					msgClass = "text-danger";
					idBox.find("input").focus();
				}
				if (idMsg.length == 0) {
					idMsg = $("<p id='idMsg'>");
					idBox.append(idMsg);
				}
				idMsg.text(message).attr("class", msgClass);

			},
			error : function(resp) {
				console.log(resp.status + ", " + resp.responseText)
			}
		});
	});

	// 네이버 캡차 연결
	function nCaptcha() {
		var naverCaptcha = $("#naverCaptcha");
		var captchaImg = $("#captchaImg");
		var captchaImgTag = $("#captchaImg").find("img");
		$.ajax({
			url : "${pageContext.request.contextPath}/nCaptcha.do",
			method : "get",
			dataType : "json",
			success : function(resp) {
				if (captchaImgTag.length <= 0) {
					captchaImgTag = $('<img>').addClass("w-100");
					captchaImg.append(captchaImgTag);
				}
				captchaImgTag.attr("src", "data:image/*;base64,"
						+ resp.nCaptcha);
			},
			error : function(resp) {
				console.log(resp.status + ", " + resp.responseText)
			}
		});
	}
	nCaptcha();

	//캡차 인증 요청
	function nCaptchaResult() {
		var userCaptchaStr = $("#userCaptchaStr").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/nCaptcha.do",
			method : "post",
			dataType : "json",
			data : {
				userCaptchaStr : userCaptchaStr
			},
			success : function(resp) {
				if (resp.result.indexOf("true") > 0) {
					alert("인증 성공");
					userForm.data("captchaChecked", "true");
					$("#naverCaptcha > div").hide();
					$("#naverCaptcha").append(
							$("<p class='text-success'>").text("인증 성공"));
				} else {
					alert("인증 실패");
				}
			},
			error : function(resp) {
				console.log(resp.status + ", " + resp.responseText)
			}
		});
	}
	
	$('#searchZipCode').on('click',function(){
		new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
               	document.getElementById('user_zipcode').value = data.zonecode;
                if(data.jibunAddress!=null){
                	document.getElementById("user_addr1").value = data.jibunAddress + ' ' + extraRoadAddr;
                }
                if(roadAddr!=null){
                	document.getElementById("user_addr1").value = roadAddr + ' ' + extraRoadAddr;
                }
                
                document.getElementById("user_addr2").focus();
                
                
//                 // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
//                 if(roadAddr !== ''){
//                     document.getElementById("sample4_extraAddress").value = extraRoadAddr;
//                 } else {
//                     document.getElementById("sample4_extraAddress").value = '';
//                 }

//                 var guideTextBox = document.getElementById("guide");
//                 // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
//                 if(data.autoRoadAddress) {
//                     var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
//                     guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
//                     guideTextBox.style.display = 'block';

//                 } else if(data.autoJibunAddress) {
//                     var expJibunAddr = data.autoJibunAddress;
//                     guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
//                     guideTextBox.style.display = 'block';
//                 } else {
//                     guideTextBox.innerHTML = '';
//                     guideTextBox.style.display = 'none';
//                 }
            }
        }).open();
	})
</script>



