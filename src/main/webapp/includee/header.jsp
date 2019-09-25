<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>

.dropdown:hover .nav-item-depth2 {
	z-index:0;
	transform: translate(-50% ,0%);
	transition-delay: 0s, 0s, 0.2s;
	visibility: visible;
	opacity:1;
	background:#fff;
}
.nav-item-depth2 {
	visibility:hidden;
	transform: translate(-50%, -0.5em);
	z-index: -1;
	opacity:0;
	transition: all 0.2s ease-in-out 0s, visibility 0s linear 0.3s, z-index
		0s linear 0.01s;
	background: #343a40;
	border: 1px solid #ddd;
	border-top: 0 none;
	list-style: none;
	padding: 0;
	position: absolute;
	width: 150px;
	top: 52px;
	left: 50%;
	text-align: center;
}

.nav-item-depth2 .depth2-item a {
	color: #000;
	display: block;
	padding: 9px 15px;
	border-top: 1px solid rgba(0, 0, 0, 0.08);
}

.nav-item {
	position: relative;
}

.nav-item-depth2 .depth2-item a:hover {
	text-decoration: none;
	background: #e8ecec;
}
</style>
<header>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/"
			id="logo"><i class="fas fa-pen-square mr-2"></i>Help! Sem</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarCollapse" aria-controls="navbarCollapse"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav m-auto">
				
				<li class="nav-item ml-3 mr-3"><a class="nav-link"
					href='<c:url value="/board/list.do?type=NO"/>'>공지사항</a></li>
				<li class="nav-item ml-3 mr-3"><a class="nav-link"
					href='<c:url value="/lecture/lectureList.do?type=LL"/>'>강의목록</a></li>
				<li class="nav-item dropdown ml-3 mr-3"><a href="#" class="nav-link">커뮤니티</a>
					<ul class="nav-item-depth2">
						<li class="depth2-item"><a
							href='<c:url value="/board/list.do?type=ID"/>'>정보자료<span
								class="sr-only">(current)</span></a></li>
						<li class="depth2-item"><a
							href='<c:url value="/board/list.do?type=NE"/>'>신기술</a></li>
						<li class="depth2-item"><a
							href='<c:url value="/board/list.do?type=FA"/>'>질문게시판</a></li>
					</ul>
				</li>
			</ul>
		</div>

		<!--     <nav class="collapse navbar-collapse" id="navbarCollapse"> -->
		<!--       <ul class="navbar-nav ml-auto"> -->
		<!--         <li class="nav-item active"> -->
		<!--           <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a> -->
		<!--         </li> -->
		<!--         <li class="nav-item"> -->
		<!--           <a class="nav-link" href="#">Link</a> -->
		<!--         </li> -->
		<!--         <li class="nav-item"> -->
		<!--           <a class="nav-link disabled" href="#">Disabled</a> -->
		<!--         </li> -->
		<!--       </ul> -->
		<!--     </nav> -->
		<div>
			<ol class="mb-0 pl-3">
				<c:choose>
					<c:when test="${empty loginUser }">
						<li class="rounded-circle"><a href="<c:url value="/login" />"><i
								class="fas fa-unlock-alt"></i><span class="sr-only">로그인</span></a></li>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test='${loginUser.user_type eq "tutor" }'>
								<li class="rounded-pill"><a
									href="<c:url value="/tutor/mypage/mypageHome" />"><i
										class="fas fa-user-circle"></i><span class="ml-2">${loginUser.user_name } 선생님</span></a></li>
							</c:when>
							<c:when test='${loginUser.user_type eq "admin" }'>
								<li class="rounded-circle"><a
									href="<c:url value="/admin/report" />"><i
										class="fas fa-tools"></i><span class="sr-only">관리자 페이지</span></a></li>
							</c:when>
							<c:otherwise>
								<li class="rounded-pill"><a
									href="<c:url value="/tutee/mypage/mypageHome" />"><i
										class="fas fa-user-circle"></i><span class="ml-2">${loginUser.user_name } 학생</span></a></li>
							</c:otherwise>
						</c:choose>
						<li class="rounded-circle"><a
							href="<c:url value="/login/logout.do" />"><i
								class="fas fa-sign-out-alt"></i><span class="sr-only">로그아웃</span></a></li>
					</c:otherwise>
				</c:choose>
			</ol>
		</div>
		<c:if test='${not empty loginUser and loginUser.user_type ne "admin"}'>
			<button type="button" class="btn btn-info" id="myClass">
				<i class="fas fa-book-reader mr-3"></i>내 강의실
			</button>
		</c:if>
	</nav>
	<c:if
		test='${not empty loginUser and loginUser.user_type ne "admin"}'>
		<div class="popMenu">
		<button type="button" onclick="reportReasonsList();" class="btn btn-danger">
			<i class="fas fa-user-alt-slash"></i>
			<span class="sr-only">신고하기</span></button>
		<button type="button" onclick="messageList();" class="btn btn-info" data-toggle="modal"
			data-target="#messageModal"><i class="far fa-envelope mr-1"
				id="messageIcon"></i>
				<span class="sr-only">쪽지함</span></button>
		</div>
	</c:if>
</header>
<style>
#myClass {
	top: -9px;
	right: -16px;
	border-radius: 0;
	margin-bottom: -17px;
	z-index: 999;
	position: relative;
	height: 64px;
}

.navbar {
	height: 64px;
}

.navbar .rounded-circle,
.navbar .rounded-pill {
	background: rgba(255, 255, 255, 0.3);
	width: 47px;
	height: 47px;
	display: inline-block;
	margin: 0 4px;
	vertical-align: top;
}
.navbar .rounded-pill{
	width:auto;
	padding:0px 15px;
}
.navbar .rounded-pill span{
	font-size:1rem;
	position: relative;
    top: -3px;
}
.navbar .rounded-circle:hover,
.navbar .rounded-pill:hover {
	background: #7ce1e4;
}

.navbar .rounded-circle a,
.navbar .rounded-pill a {
	display: block;
	width: 100%;
	height: 100%;
	text-align: center;
	font-size: 1.3rem;
	line-height: 3rem;
	color: #fff;
}
.popMenu {
    position: fixed;
    right: 25px;
    bottom: 25px;
    z-index:500;
}
.popMenu button {
    display: block;
    width: 63px;
    height: 63px;
    border-radius: 50%;
    margin-top: 15px;
    text-align: center;
    color:#fff;
}

.popMenu button:hover{
	cursor:pointer;
}

.navbar-dark .navbar-nav .nav-link {
    color: #fff;
    font-weight: 700;
}
.navbar-dark .navbar-nav .nav-link:hover{
	color:#5fcede;
}

</style>


<div class="modal fade" id="blackInsertModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">신고</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="blackInsertForm" method="POST"
					action="${pageContext.request.contextPath}/admin/reportReason"
					enctype="multipart/form-data">
					<input type="hidden" name="rr_writer" value="${loginUser.user_id }" />
					<table>
						<tr>
							<th>신고할 아이디</th>
							<td class="form-inline">
								<input type="text" name="rr_recipient" class="form-control" style="width: 180px;"/>
								<input type="button" class="btn btn-primary" value="검색" style="margin-left: 5px;" id="searchReportBtn">
							</td>
						</tr>
						<tr>
							<th>사유</th>
							<td><select id="blackSelectList" name="rr_reason"
								class="form-control">

							</select></td>
						</tr>
						<tr>
							<th>기타사유</th>
							<td><input type="text" name="rr_content" id="blackContent"
								class="form-control" /></td>
						</tr>
						<tr>
							<th>첨부이미지</th>
							<td>
								<!-- 	      				<input type="file" name="rr_img" accept="image/*" class="form-control" /> -->
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroupFileAddon04">Upload</span>
									</div>
									<div class="custom-file">
										<input type="file" class="custom-file-input" name="rr_img"
											accept="image/*" id="inputGroupFile04"
											aria-describedby="inputGroupFileAddon04"> <label
											class="custom-file-label" for="inputGroupFile04">Choose
											file</label>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="blackAddBtn" class="btn btn-primary">확인</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="messageModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">메시지함</h5>
				<button type="button" class="close" data-dismiss="modal"
					onClick="window.location.reload(true)" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<input type="hidden" name="loginId" value="${loginUser.user_id }" />
				<input type="hidden" name="replyRecipient" /> <input type="hidden"
					name="deleteMessageNum" /> <input type="hidden" name="delMethod"
					value="delete" />
			</div>
			<div class="modal-body" id="messageDiv">
				<table id="messageTable" class="table">

				</table>
				<form id="messageForm" enctype="multipart/form-data">
					<input type="hidden" name="msg_writer" /> <input type="hidden"
						name="msg_recipient" /> <input type="hidden" name="msg_title" />
					<input type="hidden" name="msg_content" />
					<div class="input-group mb-1 msgFiles">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
						</div>
						<div class="custom-file">
							<input type="file" class="custom-file-input" name="msg_files"
								id="inputGroupFile01" aria-describedby="inputGroupFileAddon01">
							<label class="custom-file-label" for="inputGroupFile01">Choose
								file</label>
						</div>
					</div>
					<div class="input-group mb-1 msgFiles">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupFileAddon02">Upload</span>
						</div>
						<div class="custom-file">
							<input type="file" class="custom-file-input" name="msg_files"
								id="inputGroupFile02" aria-describedby="inputGroupFileAddon02">
							<label class="custom-file-label" for="inputGroupFile02">Choose
								file</label>
						</div>
					</div>
					<div class="input-group mb-1 msgFiles">
						<div class="input-group-prepend">
							<span class="input-group-text" id="inputGroupFileAddon03">Upload</span>
						</div>
						<div class="custom-file">
							<input type="file" class="custom-file-input" name="msg_files"
								id="inputGroupFile03" aria-describedby="inputGroupFileAddon03">
							<label class="custom-file-label" for="inputGroupFile03">Choose
								file</label>
						</div>
					</div>
					<!-- 			<input type="file" name="msg_files" /> -->
					<!-- 			<input type="file" name="msg_files" /> -->
					<!-- 			<input type="file" name="msg_files" /> -->
				</form>
				<div id='messagePagingArea'></div>
			</div>
			<div class="modal-footer" id="messageFooter">
				<button class="messageBackBtn btn btn-light mr-auto">◀</button>
				<button type="button" id="messageInsertBtn" class="btn btn-primary">확인</button>
				<button type="button" class="btn btn-secondary"
					onClick="window.location.reload(true)" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<script>
	var blackModal = $('#blackInsertModal');
	var selectList = $('#blackSelectList');
	var content = $('#blackContent');
	var insertForm = $('#blackInsertForm');

	var messageDiv = $('#messageDiv');
	var messageForm = $('#messageForm');
	var messageTable = $('#messageTable');
	var messageModal = $('#messageModal');
	var messageListBody = $('#messageListBody');
	var messagePagingArea = $('#messagePagingArea');
	var messageInsertBtn = $('#messageInsertBtn');
	var modalFooter = $('#messageFooter');
	var messageBackBtn = $('.messageBackBtn');

	//내 강의실
	var myLecture = $('#myClass');

	function messagePaging(page) {
		if (page <= 0)
			return;
		$('#sendMessageBtn').remove();
		messageList(page);
	}

	$('.custom-file-input').on('change', function() { 
		var fileName = $(this).val();
		var filename = fileName.substring(fileName.lastIndexOf("\\") + 1);
		$(this).next(".custom-file-label").html(filename);
	})

	function messageCount() {
		var loginId = $('input[name=loginId]').val();
		$.ajax({
			url : "${pageContext.request.contextPath}/message/messageCount",
			method : "get",
			data : {
				"loginId" : loginId
			},
			dataType : "json",
			success : function(resp) {
				$("#messageIcon").after(
						"<span class='badge badge-light'>" + resp + "</span>");
			}
		});
	}
	messageCount();

	function messageList(page) {
		var loginId = $('input[name=loginId]').val();
		$.ajax({
			url : "${pageContext.request.contextPath}/message/messageList",
			method : "get",
			data : {
				"page" : page,
				"loginId" : loginId
			},
			dataType : "json",
			success : function(resp) {
				let dataList = resp.dataList;
				var tags = [];
				tags.push($('<thead>').append(
						$('<tr>').append($('<th>').text("번호")).append(
								$('<th>').text("보낸사람")).append(
								$('<th>').text("제목")).append(
								$('<th>').text("날짜"))));
				var trTags = [];
				if(dataList.length == 0){
					var tr = $('<tr>').append($('<td>').text("받은 쪽지가 없음").attr({"colspan" : "4"}).css({"text-align" : "center"}));
					trTags.push(tr);
				}
				$(dataList).each(
						function(idx, msg) {
							var writer = "";
							if (msg.msg_writer == "admin")
								writer = "관리자";
							else
								writer = msg.msg_writer;
							
							var len = 10;
			            	var lastTxt = "...";
			            	var title = msg.msg_title;
			            	if(msg.msg_title.length > len){
			            		title = msg.msg_title.substr(0, len) + lastTxt;
			            	}

							var tr = $('<tr>').append($('<td>').text(msg.rnum))
									.append($('<td>').text(writer)).append(
											$('<td>').text(title))
									.append($('<td>').text(msg.msg_date)).prop(
											"id", msg.msg_no).prop('class',
											'messageTr');
							//읽었다는 표시
							if (msg.msg_ok == 1) {
								tr.css('color', 'lightgray');
							}
							trTags.push(tr);
						});
				tags.push($('<tbody>').append(trTags).prop("id",
						"messageListBody"));
				// 				tags.push($('<button>').text('보내기')
				// 								.prop({'id': 'sendMessageBtn', "class" : "btn btn-secondary"}));
				messageTable.html(tags);
				messageTable.after($('<button>').text('보내기').prop({
					'id' : 'sendMessageBtn',
					"class" : "btn btn-secondary"
				}));
				messagePagingArea.html(resp.pagingHTMLForBS);
				// 				$('input[name=msg_files]').attr('type', 'hidden');
				$('.msgFiles').hide();
				modalFooter.hide();
				messageBackBtn.hide();
			}
		});
	};
	messageTable
			.on(
					'click',
					'.messageTr',
					function() {
						let msg_no = $(this).prop('id');
						var loginId = $('input[name=loginId]').val();

						$
								.ajax({
									url : "${pageContext.request.contextPath}/message/messageView?what="
											+ msg_no,
									method : "get",
									dataType : "json",
									success : function(resp) {
										var aTags = [];
										$(resp.attachList)
												.each(
														function(idx, attach) {
															aTags
																	.push($(
																			'<a>')
																			.text(
																					attach.att_filename)
																			.attr(
																					{
																						"href" : "${pageContext.request.contextPath}/download.do?what="
																								+ attach.att_no
																					}));
															aTags
																	.push($('<br>'));
														});

										var writer = "";
										if (resp.msg_writer == "admin")
											writer = "관리자";
										else
											writer = resp.msg_writer;

										var tags = [];
										tags.push($("<tr>").append(
												$('<th>').text("보낸사람")).append(
												$('<td>').text(writer)));
										tags.push($("<tr>").append(
												$('<th>').text("날짜")).append(
												$('<td>').text(resp.msg_date)));
										tags
												.push($("<tr>")
														.append(
																$('<th>').text(
																		"제목"))
														.append(
																$('<td>')
																		.text(
																				resp.msg_title)));
										tags
												.push($("<tr>")
														.append(
																$('<th>').text(
																		"내용"))
														.append(
																$('<td>')
																		.text(
																				resp.msg_content)));
										if (resp.attachList.length != 0) {
											tags.push($("<tr>").append(
													$('<th>').text("첨부파일"))
													.append(
															$('<td>').append(
																	aTags)));
										}

										// 				if(resp.msg_writer!="admin"){
										// 					tags.push($('<button>').text('답장')
										// 							.prop('class', 'btn btn-secondary messageReplyBtn'));
										// 				}
										// 				tags.push($('<button>').text('삭제')
										// 						.prop({'id': 'messageDeleteBtn',"class":"btn btn-secondary"}));
										$('input[name=replyRecipient]').val(
												resp.msg_writer);
										$('input[name=deleteMessageNum]').val(
												msg_no);
										messageTable.html(tags);
										messageTable
												.after($("<button>")
														.text("◀")
														.prop(
																{
																	"class" : "btn btn-light",
																	"id" : "messageBackBtn"
																}));
										messageBackBtn.show();
										messageTable.after($('<button>').text(
												'삭제').prop({
											'id' : 'messageDeleteBtn',
											"class" : "btn btn-secondary"
										}));
										$("#messageDeleteBtn")
												.after(
														$('<button>')
																.text('답장')
																.prop('class',
																		'btn btn-secondary messageReplyBtn'));
										if (resp.msg_recipient == loginId) {
											$(".messageReplyBtn")
													.after(
															$('<button>')
																	.text(
																	  		'신청하기')
																	.prop(
																			{
																				'class' : 'btn btn-secondary',
																				'id' : 'lectureAddBtn'
																			})
																	.attr(
																			'data-team',
																			resp.team_no));
										}
										;
										messagePagingArea.empty();

										$('#sendMessageBtn').remove();
									}
								});
					});

	messageBackBtn.on('click', function() {
		$('input[name=replyRecipient]').val("");
		$(".messageReplyBtn").remove();
		$("#messageDeleteBtn").remove();
		messageList();
	});
	messageDiv.on('click', '#messageBackBtn', function() {
		$('input[name=replyRecipient]').val("");
		$(".messageReplyBtn").remove();
		$('#messageBackBtn').remove();
		$("#messageDeleteBtn").remove();
		$('#lectureAddBtn').remove();
		messageList();
	});
	messageDiv.on('click', '.messageReplyBtn', function() {
		$('#messageBackBtn').remove();
		$('#lectureAddBtn').remove();
		messageInsertView();
	});
	messageDiv.on('click', '#sendMessageBtn', function() {
		messageInsertView();
	});
	// 	messageTable.on('click','#sendMessageBtn', function(){ messageInsertView(); });
	function messageInsertView() {
		var user_id = $('input[name=searchWord]').val();
		var replyRecipient = $('input[name=replyRecipient]').val();
		var tags = [];
		if (replyRecipient != null) {
			tags.push($("<tr>").append($('<th>').text("받는사람")).append(
					$('<td class="form-inline">').append($('<input style="width: 180px;">').attr({
						"type" : "text",
						"name" : "msgRecipient",
						"value" : replyRecipient,
						"class" : "form-control"
					}),
					$('<button class="btn btn-primary headerIdCheckBtn">').text('확인')
					
					)));
		} else {
			tags.push($("<tr>").append($('<th>').text("받는사람")).append(
					$('<td>').append($('<input style="width: 180px;">').attr({
						"type" : "text",
						"name" : "msgRecipient",
						"class" : "form-control"
					}))));
		}
		tags.push($("<tr>").append($('<th>').text("제목")).append(
				$('<td>').append($('<input>').attr({
					"type" : "text",
					"name" : "msgTitle",
					"class" : "form-control"
				}))));
		tags.push($("<tr>").append($('<th>').text("내용")).append(
				$('<td>').append($('<textarea>').attr({
					"rows" : "5",
					"cols" : "40",
					"name" : "msgContent",
					"class" : "form-control"
				}))));
		messageTable.html(tags);
		messagePagingArea.empty();
		$('input[name=replyRecipient]').val("");
		$('.msgFiles').show();
		// 		$('input[name=msg_files]').attr('type', 'file');
		modalFooter.show();
		messageBackBtn.show();
		$('#sendMessageBtn').remove();
		$(".messageReplyBtn").remove();
		$("#messageDeleteBtn").remove();
	};
	messageInsertBtn
			.on('click',
					function() {
						$("input[name=msg_recipient]").val(
								$('input[name=msgRecipient]').val());
						$("input[name=msg_title]").val(
								$('input[name=msgTitle]').val());
						$("input[name=msg_content]").val(
								$('textarea[name=msgContent]').val());
						$("input[name=msg_writer]").val(
								$('input[name=loginId]').val());

						messageForm.submit();
					});

	messageForm.on('submit', function() {
		event.preventDefault();

		var form = messageForm[0];
		var data = new FormData(form);

		$.ajax({
			url : "${pageContext.request.contextPath}/message/messageInsert",
			method : "post",
			data : data,
			processData : false,
			contentType : false,
			dataType : "json",
			success : function(resp) {
				if (resp.success) {
					messageForm[0].reset();
					messageList();
				} else {
					alert(resp.message);
				}
			}
		});
		return false;
	});
	messageDiv
			.on(
					'click',
					'#messageDeleteBtn',
					function() {
						var msg_no = $('input[name=deleteMessageNum]').val();
						var method = $('input[name=delMethod]').val();
						$
								.ajax({
									url : "${pageContext.request.contextPath}/message/messageDelete?what="
											+ msg_no,
									method : "get",
									dataType : "json",
									success : function(resp) {
										messageList();
										$(".messageReplyBtn").remove();
										$("#messageDeleteBtn").remove();
										$('#lectureAddBtn').remove();
									}
								});
						return false;
					});

	// 신고하기
	function reportReasonsList() {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/reportReasons",
			method : "get",
			dataType : "json",
			success : function(resp) {
				var options = [];
				$(resp).each(
						function(idx, reasons) {
							$(selectList).append(
									'<option value="'+reasons.rrs_no+'">'
											+ reasons.rrs_name + '</option>');
						});
				$(content).attr("readonly", true);
			}
		});
		blackModal.modal('show');
	};
	$(selectList).change(function() {
		var value = $(this).val();
		if (value == 4) {
			$(content).attr("readonly", false);
		} else {
			$(content).attr("readonly", true);
			$(content).val("");
		}
	});
	$('#blackAddBtn').on('click', function() {
		insertForm.submit();
	});

	insertForm.on('submit', function() {
		event.preventDefault();

		var queryString = $(this).serialize();
		var method = $(this).attr("method");
		var action = $(this).attr("action");

		let form = $(this)[0];
		let formData = new FormData(form);

		$.ajax({
			url : action,
			data : formData,
			method : "post",
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(resp) {
				if (resp.success) {
					insertForm[0].reset();
					blackModal.modal('hide');
				} else {
					alert(resp.message);
				}
			}
		});
		return false;
	});

	var login = $('input[name=loginId]').val();

	if (login) {
		myLecture
				.on(
						'click',
						function() {
							if(${loginUser.user_type eq "tutor" and loginUser.tt_auth eq 0 }){
								alert("인증이 필요합니다.");
							}else{
								location.href = "${pageContext.request.contextPath }/myLecture/list";
							}
						})
	};

	messageDiv
			.on(
					'click',
					'#lectureAddBtn',
					function() {
						var team_no = $(this).data('team');
						console.log(team_no);
						location.href = '${pageContext.request.contextPath }/lecture/lectureInsert.do?team='
								+ team_no;
					})
					
	$('#searchReportBtn').on('click', function(){
		var inputId = $('input[name=rr_recipient]').val();
		$('.checkSpan').text("");
		$.ajax({
			url : "<c:url value='/user/idCheck.do'/>",
			dataType : 'json',
			data : {
				"inputId" : inputId
			},
			success : function(resp){
				if(resp.duplicated){
					$('#searchReportBtn').after(
						$('<span class="checkSpan">').text('OK')		
					)
				}else {
					$('#searchReportBtn').after(
							$('<span class="checkSpan">').text('없는 아이디입니다')
									   .css('color', 'red')
						)
					$('input[name=rr_recipient]').val("");
					$('input[name=rr_recipient]').focus();
				}
			}
		})
	})
	
	messageDiv.on('click', '.headerIdCheckBtn' ,function(){
		var inputId = $('input[name=msgRecipient]').val();
		console.log(inputId);
		$('.checkSpan').text("");
		$.ajax({
			url : "<c:url value='/user/idCheck.do'/>",
			dataType : 'json',
			data : {
				"inputId" : inputId
			},
			success : function(resp){
				if(resp.duplicated){
					$('.headerIdCheckBtn').after(
						$('<span class="checkSpan">').text('OK')
					)
				}else {
					$('.headerIdCheckBtn').after(
							$('<span class="checkSpan">').text('없는 아이디입니다')
									   .css('color', 'red')
						)
					$('input[name=msgRecipient]').val("");
					$('input[name=msgRecipient]').focus();
				}
			}
		})
	})
</script>