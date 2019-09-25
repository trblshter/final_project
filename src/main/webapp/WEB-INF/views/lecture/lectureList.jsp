 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<link href="${pageContext.request.contextPath }/bootstrap-4.3.1/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/simple-sidebar.css" rel="stylesheet">
<style>
	.img {
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 5px;
		width: 100px;
	}
	.imgBtn {
		width : 20px;
		height : 20px;
	}
	.card {cursor:pointer;}
	
	.position-absolute.p-2.badge {
	    top: 0;
	    right: 0;
	    font-size: 0.9rem;
	    border-radius: 0;
	}
	.noImg {
    height: 247px;
    background: #5d6267;
    text-align: center;
	}
	
	.noImg i {
	    font-size: 7rem;
	    opacity: 0.4;
	    margin-top: 64px;
	}
</style>
<script>
	function ${pagingVO.funcName}(page){
		if(page<=0) return;
		searchForm.find("input[name='page']").val(page);
		searchForm.submit();
		searchForm.find("input[name='page']").val("");
	}
	
	$(function(){
		$('tbody#listBody').on('click', 'tr', function(){
			console.log(what);
			if(what == '학생'){
				var who = $(this).prop('id');
				location.href = "${pageContext.request.contextPath }/lecture/lectureView.do?who=" + who;
			}else if(what == '선생님'){
				var who = $(this).prop('id');
				location.href = "${pageContext.request.contextPath }/lecture/teacherView.do?who=" + who;
			}
		}).css({cursor : "pointer"});
		
		$('.lectureItemList').on('click', '.lectureItem', function(){
			
			console.log(what);
			if(what == '학생'){
				var who = $(this).prop('id');
				location.href = "${pageContext.request.contextPath }/lecture/lectureView.do?who=" + who;
			}else if(what == '선생님'){
				var who = $(this).prop('id');
				location.href = "${pageContext.request.contextPath }/lecture/teacherView.do?who=" + who;
			}
		});
		
		
		searchForm = $("#searchForm");
		teacherForm = $("#teacherForm");
		var listBody = $("tbody#listBody");
		var head = $("#head");
		var title = $("#title");
		var addBtn = $('#addBtn');
		var titleBar = $('#titleBar');
		lectureItemList = $(".lectureItemList");
		var pagingArea = $("#pagingArea");
		
		$("#allBtn").on("click", function(){
// 			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#javaBtn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#cBtn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#c2Btn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#phythonBtn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#linuxBtn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		$("#htmlBtn").on("click", function(){
			searchForm.find("input[name='searchWord']").val($(this).val());
			searchForm.submit();
		});
		
		var what = "학생";
		
		$("#student").on("click", function(){
			what = "학생";
			searchForm.submit();
		})
		$("#teacher").on("click", function(){
			what = "선생님";
			searchForm.submit();
		})
		
		$("#addBtn").on("click", function(){
			if($('#userId').val() == ""){
				alert("로그인해라");
				return false;
			}else {
				if($('#userType').val() == "tutee"){
					if(what == "선생님"){
						$('#exampleModal').modal('show')
					}else {
						alert("학생은 등록할 수 없습니다")
					}
				}else if ($('#userType').val() == "tutor"){
					if(what == '선생님'){
						alert('학생만 등록 가능합니다!!')
					}else {
						location.href="<c:url value='/lecture/lectureInsert.do'/>";
					}
				}
			}
		})
		
		searchForm.on("submit", function(event){
			var queryString = $(this).serialize();
			event.preventDefault();
			if(what == "학생" || typeof what == "undefined"){
				$.ajax({
					url : "${pageContext.request.contextPath }/lecture/lectureList.do",
					data : queryString,
					dataType : "json",
					success : function(resp) {
						searchForm.find("input[name='searchWord']").val(resp.searchWord);
						let lectureList = resp.dataList;
						title.html("&nbsp;누구나 쉽게 들을 수 있는 강의목록");
						$('#titleBar').css('width', '96.2%');
						$('.table-striped').hide();
						lectureItemList.show();
						$('#javaBtn').attr('disabled', false);
						$('#cBtn').attr('disabled', false);
						$('#c2Btn').attr('disabled', false);
						$('#phythonBtn').attr('disabled', false);
						$('#linuxBtn').attr('disabled', false);
						$('#htmlBtn').attr('disabled', false);
					    	
						$('#allBtn').show();
						$('#javaBtn').show();
						$('#cBtn').show();
						$('#c2Btn').show();
						$('#phythonBtn').show();
						$('#linuxBtn').show();
						$('#htmlBtn').show();
						addBtn.show();
						addBtn.text("강의 등록");
						var lectureListt = [];
						console.log(lectureList);
						if(lectureList && lectureList.length > 0){
							$(lectureList).each(function(idx, lecture){
								var cat_names = [];
								$(lecture.categoryList).each(function(i, cate){
									cat_names.push(cate.ctgy_name);
								});
								
								var badgeClass = '';
								var badgeText = '';
								if(lecture.lt_completed == '1'){
									badgeClass = 'badge-secondary';
									badgeText = '모집완료';
								}else{
									badgeClass = 'badge-success';
									badgeText = '모집중'
								}
								let isImg = '';
								if(lecture.lt_imageBase64){
									isImg = $('<img class="card-img-top">').attr("src","data:image/*;base64,"+ lecture.lt_imageBase64)
								}else{
									isImg = $('<div class="noImg">').append(
										$('<i class="far fa-images"></i>')	
									);
								}
									
								let lectureItem = $("<div class='lectureItem col-3 p-1 pb-5'>").prop("id",lecture.lt_no).append(
									$('<div class="card h-100 posidion-relative">').append(
											$(isImg),
											$('<div class="p-2 pl-4 pr-4 mb-2 bg-dark text-white">').append(
												$('<span>').text(cat_names)		
											),
											$('<div class="card-body">').append(
												$('<h4 class="mb-3">').text(lecture.lt_title),													
												$('<i class="far fa-calendar-alt mr-2">'),													
												$('<span>').text(lecture.lt_date),
												$('<br>'),
												$('<i class="fas fa-coins mr-2">'),													
												$('<span>').text(lecture.lt_price),
												$('<br>'),
												$('<i class="far fa-eye mr-2">'),													
												$('<span>').text(lecture.lt_views),
												$('<p class="position-absolute p-2 badge">').addClass(badgeClass).text(badgeText)										
											)
									)	
								);
								
								lectureListt.push(lectureItem);
								
							});
							pagingArea.html(resp.pagingHTMLForBS);
							lectureItemList.html(lectureListt);
						}else{
							lectureItemList.html("<p class='text-center'>조건에 맞는 항목이 없습니다. </p>");
						}
// 						listBody.html(trTags);
						pagingArea.html(resp.pagingHTMLForBS);
						$("#studentBtn").hide();
					},
					error : function(errorResp) {
						console.log(errorResp.status + ", "
								+ errorResp.responseText)
					}
				});
			}else if(what == "선생님"){
				teacherList();
			}
			$('input[name="searchWord"]').val("");
			return false;
		});
			
			var userType = $('#userType').val();
			console.log(userType);
			
			$('#teacherForm').on('submit', function(event){
				var data = $(this).serialize();
				var action = $(this).prop('action');
				event.preventDefault();
				console.log(action);
				if(userType == 'tutee'){
					$.ajax({
						url : action,
						method : "post",
						dataType : "json",
						data : data,
						success : function(resp){
							what = "선생님";
						},
						error : function(errorResp){
							alert(errorResp);
						}
					});
				}else if(userType == 'tutor') {
					alert('학생만 신청 가능합니다')
				}
			});
			<c:if test="${what eq 'TE' }">
				$("#teacher").trigger("click");
			</c:if>
			
			$('#userCheckForm').on('submit', function(event){
				event.preventDefault();
				var inputId = $('#inputId').val();
				var searchBtn = $('#searchBtn');
				var teamDiv = $('#teamDiv');
				searchBtn.next().remove();
				var url = $(this).attr('action');
				$.ajax({
					url : url,
					data : {
						"inputId" : inputId
					},
					dataType : 'json',
					success : function(resp){
						if(resp.duplicated){
// 							searchBtn.after(
// 								$('<button style="margin-bottom: 8px; margin-left:5px;">').prop({
// 									'class' : 'btn btn-info',
// 									'id'    : 'userId'
// 								})
// 											                                              .text('사용가능 아이디')
// 											                                              .attr(
// 											                                            	'data-user', inputId	  
// 											                                              )
// 							)
							teamDiv.append(
								$('<span>').append(
										$('<input style="border: 0; width: 85px;">').val(inputId)
										.prop({
											'readonly' : true,
											'class'    : 'user'
										})
										,$('<input type="image" style="margin-right: 10px;">').prop({
											'src' : '${pageContext.request.contextPath }/image/x.png',
											'class' : 'imgBtn'
										})										
								)
							)
						}else {
							searchBtn.after(
								$('<label style="margin-left:10px;">').text('사용불가능 아이디')
												.prop({
													'id' : 'notId'
												})
							)
						}
					}
				})
				$('#inputId').val("");
				$('#inputId').focus();
			});
			
			$('#teamDiv').on('click', '.imgBtn', function(){
				var what = $(this).closest("span").remove();
				console.log(what);
				return false;
			})
		
			$('#lectureUserAddBtn').on('click', function(){
				var teams = $('#teamDiv').find('.user');
				teams.each(function(idx, team){
					$(team).attr({
						'name' : 'team_member'+(idx+2)
					}) 					
				})
				
				$('#teacherForm').submit();
				$('#exampleModal').modal('hide');
				$('#title').val("");
				$('#content').val("");
				searchForm.submit();
			})
			
			function teacherList(){
				$('.table-striped').show();
				lectureItemList.hide();
				lectureItemList.empty();
					$.ajax({
					url : "${pageContext.request.contextPath }/teacher/teacherList.do",
					dataType : "json",
					success : function(resp) {
						let lectureList = resp.dataList;
						console.log(lectureList);
						title.html("&nbsp;자유롭게 볼 수 있는 목록");
						$('#titleBar').css('width', '60%');
// 						$('#javaBtn').attr('disabled', true);
// 						$('#cBtn').attr('disabled', true);
// 						$('#c2Btn').attr('disabled', true);
// 						$('#phythonBtn').attr('disabled', true);
// 						$('#linuxBtn').attr('disabled', true);
// 						$('#htmlBtn').attr('disabled', true);
						
						$('#allBtn').hide();
						$('#javaBtn').hide();
						$('#cBtn').hide();
						$('#c2Btn').hide();
						$('#phythonBtn').hide();
						$('#linuxBtn').hide();
						$('#htmlBtn').hide();

						addBtn.text('글 등록');
						var trTags = [];
						var trHead = [];
						var tHead = $("<tr style='width: 60%; text-align: center;'>");
						tHead.append(
							$("<th style='width: 40px;'>").html("번호")
							,$("<th style='width: 45px;'>").html("작성자")
							,$("<th style='width: 400px;'>").html("제목")
							,$("<th style='width: 80px;'>").html("조회수")
						)
						trHead.push(tHead);
						head.html(trHead);
						if(lectureList && lectureList.length > 0){
							$(lectureList).each(function(idx, lecture){
								var tr = $("<tr>").prop("id", lecture.lu_no);
								tr.append(
										$("<td style='text-align:center; vertical-align: middle;'>").html(
											lecture.rnum
										)		
										, $("<td style='text-align:center;'>").html(lecture.lu_writer)		
										, $("<td >").html(lecture.lu_title)
										, $("<td style='text-align:center;'>").html(lecture.lu_views)
								);
								trTags.push(tr);
							});
						}else{
							trTags.push(
							  $("<tr>").append(
								$("<td>").prop({colspan:5})
									     .text("조건에 맞는 글이 없음.")
							  )
							);
						}
						
						listBody.html(trTags);
						pagingArea.html(resp.pagingHTMLForBS);
	//						$("#studentBtn").show();
					},
					error : function(errorResp) {
						console.log(errorResp.status + ", "
								+ errorResp.responseText)
					}
				});
			}
						
	});
</script>
<div class="d-flex" id="wrapper">
	<input type="hidden" value="${loginUser.user_id }" id="userId">
	<input type="hidden" value="${loginUser.user_type }" id="userType">
	<!-- Sidebar -->
	<div class="bg-light border-right" id="sidebar-wrapper">
		<div class="sidebar-heading d-flex justify-content-center" style="height: 63px;">항목</div>
		<div class="list-group list-group-flush">
			<button class="list-group-item list-group-item-action bg-light" id="student" value="학생"><i class="fas fa-check">&nbsp;학생모집</i></button>
			<button class="list-group-item list-group-item-action bg-light" id="teacher" value="선생님"><i class="fas fa-check">&nbsp;선생님모집</i></button>
		</div>
	</div>
	<!-- /#sidebar-wrapper -->

	<!-- Page Content -->
	<div id="page-content-wrapper">
	
		<nav
			class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
			<button class="btn btn-primary" id="menu-toggle">Toggle Menu</button>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</nav>
		
		<button class="btn btn-warning" id="allBtn" >전체</button>
		<button class="btn btn-warning" id="javaBtn" value="자바" >자바</button>
		<button class="btn btn-warning" id="cBtn" value="C++">C++</button>
		<button class="btn btn-warning" id="c2Btn" value="C#">C#</button>
		<button class="btn btn-warning" id="phythonBtn" value="Phython">Phython</button>
		<button class="btn btn-warning" id="linuxBtn" value="LINUX">LINUX</button>
		<button class="btn btn-warning" id="htmlBtn" value="HTML">HTML</button><br><br>
		
	<div id="titleBar" style="width: 96.2%; margin-left: 10px;">
		<i class="fas fa-angle-right" id="title">&nbsp;누구나 쉽게 들을 수 있는 강의목록</i>
		<button class="btn btn-primary btn-sm float-right" id="addBtn">강의 등록</button>
	</div>
		
		<form id="searchForm">
<%-- 			<input type="hidden" name="searchType" value="${pagingVO.searchType}" /> --%>
			<input type="hidden" name="searchWord" value="${pagingVO.searchWord}" />
			<input type="hidden" name="page" />
		</form>
		<c:set var="list" value="${pagingVO.dataList }" />
		<div class="lectureItemList row p-5 m-0 w-100">
		<c:if test="${not empty list }">
			<c:forEach var="lectureTutor" items="${list }">
			<c:set var="categoryList" value="${lectureTutor.categoryList }" />
				<div class="lectureItem col-3 p-1 mb-5" id="${lectureTutor.lt_no }">
					<div class="card h-100 posidion-relative">
						<!--과목 이미지 -->
						<c:if test="${not empty lectureTutor.lt_imageBase64 }">
							<img class="card-img-top" src="data:image/*;base64,${lectureTutor.lt_imageBase64}">
						</c:if>
						<c:if test="${empty lectureTutor.lt_imageBase64 }">
							<div class="noImg">
								<i class="far fa-images"></i>
							</div>
						</c:if>
						<!--카테고리명 -->
						<div class="p-2 pl-4 pr-4 mb-2 bg-dark text-white">
							<c:forEach var="categorys" items="${categoryList }">
								<span>${categorys.ctgy_name}</span>
							</c:forEach>
						</div>
						<div class="card-body">
							<h4 class="mb-3">${lectureTutor.lt_title }</h4>
							<i class="far fa-calendar-alt mr-2"></i>${lectureTutor.lt_date }<br>
							<i class="fas fa-coins mr-2"></i>${lectureTutor.lt_price }<br>
							<i class="far fa-eye mr-2"></i>${lectureTutor.lt_views }
							<p class="position-absolute p-2 badge badge${lectureTutor.lt_completed eq '1' ? '-secondary' : '-success' }">${lectureTutor.lt_completed eq '1' ? '모집완료' : '모집중' }</p>
						</div>	
					</div>
				</div>
			</c:forEach>
		</c:if>
		</div>
	
		
	<table class="table table-striped" style="margin-top: 10px; margin-left: 10px; width: 60%;">
		<thead class="head" style="text-align: center;" id="head">
<!-- 			<tr style="width: 60%;"> -->
<!-- 				<th style="width: 70px;">구분</th> -->
<!-- 				<th style="width: 70px;"></th> -->
<!-- 				<th style="width: 400px;">과정소개</th> -->
<!-- 				<th style="width: 100px;">조회수</th> -->
<!-- 			</tr> -->
		</thead>
		<tbody id="listBody">
<%-- 			<c:set var="list" value="${pagingVO.dataList }" /> --%>
<%-- 			<c:if test="${not empty list }"> --%>
<%-- 				<c:forEach var="lectureTutor" items="${list }"> --%>
<%-- 				<c:set var="categoryList" value="${lectureTutor.categoryList }" /> --%>
<%-- 					<tr id="${lectureTutor.lt_no }"> --%>
<!-- 						<td style="text-align: center; vertical-align: middle;"> -->
<%-- 						<c:forEach var="categorys" items="${categoryList }"> --%>
<%-- 							${categorys.ctgy_name}<br> --%>
<%-- 						</c:forEach> --%>
<!-- 						</td> -->
						
<!-- 						<td style="text-align: center; vertical-align: middle;"> -->
<%-- 							<c:if test="${not empty lectureTutor.lt_imageBase64 }"> --%>
<%-- 								<img class="img" src="data:image/*;base64,${lectureTutor.lt_imageBase64}"> --%>
<%-- 							</c:if> --%>
<!-- 						</td> -->
<!-- 						<td> -->
<%-- 							<i class="fas fa-angle-right"></i>&nbsp;${lectureTutor.lt_title } --%>
<%-- 							<button class="btn btn-primary btn-sm" id="viewBtn">${lectureTutor.lt_completed eq '1' ? '모집완료' : '모집중' }</button> --%>
<!-- 							<br> -->
<%-- 							<i class="fas fa-angle-right"></i>&nbsp;${lectureTutor.lt_date }<br> --%>
<%-- 							<i class="fas fa-angle-right"></i>&nbsp;${lectureTutor.lt_price } --%>
<!-- 						</td> -->
<!-- 						<td style="vertical-align: middle; text-align: center;"> -->
<%-- 							${lectureTutor.lt_views } --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
<%-- 			</c:if> --%>
		</tbody>
	</table>
	<div id="pagingArea" style="width: 70%; margin-left: 10px; display: inline-block;">${pagingVO.pagingHTMLForBS }</div>
	
	</div>
</div>
<!-- /#wrapper -->

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">글 등록</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <div class="modal-body" id="teamModal">
        <form action="<c:url value='/user/idCheck.do'/>" id="userCheckForm">
          <div class="form-group row">
            <label for="inputPrice3" class="col-sm-2 col-form-label" style="text-align: center;">팀원추가</label>
       	  	<div class="col-sm-10 form-inline">	
			    <input type="text" class="form-control" id="inputId" name="inputId" style="width: 200px;">
			  	<button type="submit" class="btn btn-primary" id="searchBtn" style="margin-left: 10px;">검색</button>
		  	</div>
		  </div>
		</form>
		
		<form action="<c:url value='/lecture/teacherInsert.do'/>" id="teacherForm">
          <div class="form-group row">
            <label for="inputPrice3" class="col-sm-2 col-form-label" style="text-align: center;">팀원</label>
				<div id="teamDiv" class="col-sm-10 form-inline">
					
				</div>
          </div>
          
          <div class="form-group row">
            <label for="inputPrice3" class="col-sm-2 col-form-label" style="text-align: center;">제목</label>
            <div class="col-sm-10 form-inline">
            	<input type="text" class="form-control" id="title" name="lu_title" style="width: 300px;">
            </div>
          </div>
          
          <div class="form-group row">
            <label for="inputPrice3" class="col-sm-2 col-form-label" style="text-align: center;">내용</label>
            <div class="col-sm-10 form-inline">
            	<textarea class="form-control" id="lu_content" name="lu_content" style="width: 700px; height: 200px;"></textarea>
            </div>
          </div>
          <input type="hidden" name="lu_writer" value="${loginUser.user_id }">
         </form>
          
	      <div class="modal-footer" style="padding-right: 1px;">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	        <input type="button" class="btn btn-primary" value="등록" id="lectureUserAddBtn">
	      </div>
      </div>
    </div>
  </div>
</div>
<!-- Modal -->

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script src="${pageContext.request.contextPath }/bootstrap-4.3.1/js/bootstrap.bundle.min.js"></script>

<!-- Menu Toggle Script -->
<script>
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
</script>


