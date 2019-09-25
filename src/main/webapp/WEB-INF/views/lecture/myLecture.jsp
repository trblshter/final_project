<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>


<link href="${pageContext.request.contextPath }/bootstrap-4.3.1/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/simple-sidebar.css" rel="stylesheet">

<!-- calendar css import -->
<link rel=" shortcut icon" href="image/favicon.ico">

<link rel="stylesheet" href="<c:url value='/vendor/css/fullcalendar.min.css'/>" />
<%-- <link rel="stylesheet" href="<c:url value='/vendor/css/bootstrap.min.css'/>"> --%>
<style>

.dropdown, .dropup {
	position: relative
}

.dropdown-toggle:focus {
	outline: 0
}

.dropdown-menu {
	position: absolute;
	top: 100%;
	left: 0;
	z-index: 1000;
	display: none;
	float: left;
	min-width: 160px;
	padding: 5px 0;
	margin: 2px 0 0;
	font-size: 14px;
	text-align: left;
	list-style: none;
	background-color: #fff;
	-webkit-background-clip: padding-box;
	background-clip: padding-box;
	border: 1px solid #ccc;
	border: 1px solid rgba(0, 0, 0, .15);
	border-radius: 4px;
	-webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
	box-shadow: 0 6px 12px rgba(0, 0, 0, .175)
}

.dropdown-menu.pull-right {
	right: 0;
	left: auto
}

.dropdown-menu .divider {
	height: 1px;
	margin: 9px 0;
	overflow: hidden;
	background-color: #e5e5e5
}

.dropdown-menu>li>a {
	display: block;
	padding: 3px 20px;
	clear: both;
	font-weight: 400;
	line-height: 1.42857143;
	color: #333;
	white-space: nowrap
}

.dropdown-menu>li>a:focus, .dropdown-menu>li>a:hover {
	color: #262626;
	text-decoration: none;
	background-color: #f5f5f5
}

.dropdown-menu>.active>a, .dropdown-menu>.active>a:focus, .dropdown-menu>.active>a:hover
	{
	color: #fff;
	text-decoration: none;
	background-color: #337ab7;
	outline: 0
}

.dropdown-menu>.disabled>a, .dropdown-menu>.disabled>a:focus,
	.dropdown-menu>.disabled>a:hover {
	color: #777
}

.dropdown-menu>.disabled>a:focus, .dropdown-menu>.disabled>a:hover {
	text-decoration: none;
	cursor: not-allowed;
	background-color: transparent;
	background-image: none;
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=false)
}

.open>.dropdown-menu {
	display: block
}

.open>a {
	outline: 0
}

.dropdown-menu-right {
	right: 0;
	left: auto
}

.dropdown-menu-left {
	right: auto;
	left: 0
}

.dropdown-header {
	display: block;
	padding: 3px 20px;
	font-size: 12px;
	line-height: 1.42857143;
	color: #777;
	white-space: nowrap
}

.dropdown-backdrop {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 990
}

.pull-right>.dropdown-menu {
	right: 0;
	left: auto
}

.dropup .caret, .navbar-fixed-bottom .dropdown .caret {
	content: "";
	border-top: 0;
	border-bottom: 4px dashed;
	border-bottom: 4px solid\9

}

.dropup .dropdown-menu, .navbar-fixed-bottom .dropdown .dropdown-menu {
	top: auto;
	bottom: 100%;
	margin-bottom: 2px
}

</style>
<link rel="stylesheet" href='<c:url value="/vendor/css/select2.min.css"/>' />
<link rel="stylesheet" href='<c:url value="/vendor/css/bootstrap-datetimepicker.min.css"/>'/>

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">

<link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
<c:if test="${not empty message}">
	<script>
    	alert("${message}");
		<c:remove var="message" scope="session"/>
	</script>
</c:if>

<div class="d-flex" id="wrapper">
	<!-- Sidebar -->
<!-- 	<div class="bg-light border-right side-one" id="sidebar-wrapper" style="width : 240px; height : 986px;"> -->
<!-- 		<div class="sidebar-heading d-flex justify-content-center" style="width: 240px; height: 56px; font-size: 19.2px; padding: 14px 20px 14px 20px"><h2>내 강의실</h2></div> -->
<!-- 		<div class="list-group list-group-flush" style="width: 100%;"> -->
<!-- 			<button class="list-group-item list-group-item-action bg-light" id="list" value="list"><i class="fas fa-check">&nbsp;나의 강의 목록</i></button> -->
<!-- 			<button class="list-group-item list-group-item-action bg-light" id="timeTable" value="calendar"><i class="fas fa-check">&nbsp;나의 강의 현황</i></button> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!-- /#sidebar-wrapper -->

	<!-- Page Content -->
	<div id="page-content-wrapper">
		<nav
			class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
<!-- 			<button class="btn btn-primary" id="menu-toggle">Toggle Menu</button> -->

			<ul class="nav nav-pills justify-content-center mt-2">
			  <li class="nav-item">
			    <button id="list" value="list" class="nav-link ${category eq 'list'? 'active ':'' }btn">나의 강의 목록</button>
			  </li>
			  <li class="nav-item">
			    <button id="timeTable" value="calendar" class="nav-link ${category eq 'calendar'? 'active ':'' }btn">나의 강의 현황</button>
			  </li>
			</ul>
<!-- 			<button class="navbar-toggler" type="button" data-toggle="collapse" -->
<!-- 				data-target="#navbarSupportedContent" -->
<!-- 				aria-controls="navbarSupportedContent" aria-expanded="false" -->
<!-- 				aria-label="Toggle navigation"> -->
<!-- 				<span class="navbar-toggler-icon"></span> -->
<!-- 			</button> -->

			<!-- <div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
					<li class="nav-item active"><a class="nav-link" href="#">Home
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> Dropdown </a>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#">Action</a> <a
								class="dropdown-item" href="#">Another action</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">Something else here</a>
						</div></li>
				</ul>
			</div> -->
		</nav>
		<div id="myLectureFrag">
			<c:if test="${category eq 'list' }">
				<%@include file="/WEB-INF/views/lecture/subPages/myList.ejs" %>
			</c:if>
			<c:if test="${category eq 'calendar' }">
				<%@include file="/WEB-INF/views/lecture/subPages/myTimeTable.ejs" %>
			</c:if>
		</div>
	</div>
</div>

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script src="${pageContext.request.contextPath }/bootstrap-4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
	function paging(page){
		if(page<=0) return;
		searchForm.find("input[name='page']").val(page);
		searchForm.submit();
		searchForm.find("input[name='page']").val("");
	}
	
	var listBtn = $("button#list");
	var calendarBtn = $("button#timeTable");
	
	listBtn.on("click",function(){
		location.href="${pageContext.request.contextPath }/myLecture/list";
	})
	
	calendarBtn.on("click",function(){
		location.href="${pageContext.request.contextPath }/myLecture/calendar";
	})
	
// 	$('tbody#listBody').on('click', 'a', function(){
// 		var what = $(this).prop('id');
// 		location.href = "${pageContext.request.contextPath }/lecture/lectureView.do?what=" + what;
// 		console.log(what);
// 	}).css({cursor : "pointer"});
	
	let searchForm = $("#searchForm");
	var listBody = $("tbody#listBody");
	var head = $("#head");
	var titleBar = $('#titleBar');
	var pagingArea = $("#pagingArea");
	var what;
	var createLecture = $("button#createLecture");
	
	
	createLecture.on("click", function(){
		$.ajax({
			
			
			
			
		})
	})
	
	searchForm.on("submit", function(event){
		var queryString = $(this).serialize();
		event.preventDefault();
			$.ajax({
				url : "${pageContext.request.contextPath }/myLecture/list",
				data : queryString,
				dataType : "json",
				method : 'post',
				success : function(resp) {
					let myLectureList = resp.dataList;
					var trTags = [];
					var trHead = [];
					if(myLectureList && myLectureList.length > 0){
						$(myLectureList).each(function(idx, lecture){ myLectureList[idx]
							var tr = $("<tr>").prop("id", lecture.lt_no);
							var lt_status = '';
							var detailUrl;
							var roomUrl;
							if(lecture.lt_class_com == 0){
								lt_status = '진행 중';
							}else if(lecture.lt_class_com == 1){
								lt_status = '완료';
							}
							var lt_startDate = "";
							var lt_endDate = ""; 
							if(lecture.lt_start_date) lt_startDate = lecture.lt_start_date;
							if(lecture.lt_end_date) lt_endDate = lecture.lt_end_date;
							var date = lt_startDate + " ~ " + lt_endDate;
							detailUrl = '${pageContext.request.contextPath}/myLecture/detail/'+lecture.lt_no;
							roomUrl = '${pageContext.request.contextPath}/room/'+lecture.lt_no;
							tr.append(
									$("<td style='text-align:center; vertical-align: middle;'>").html(
										lecture.rnum
									)		
									, $("<td style='text-align:center; vertical-align: middle;'>").html(
										"<a href='"+detailUrl+"'>"+lecture.lt_title+"</a>"
									)		
									, $("<td style='text-align:center; vertical-align: middle;'>").html(
										lt_status
									)
									, $("<td style='text-align:center; vertical-align: middle;'>").html(
										'<a href="'+roomUrl+'" class="btn btn-dark" id="createLecture" >입장</a>'
									)
									, $("<td style='text-align:center; vertical-align: middle;'>").html(
											date
									)
								);		
							trTags.push(tr);
						});
					}else{
						trTags.push(
						  $("<tr>").append(
							$("<td>").prop({colspan:5})
								     .text("신청한 수강 목록이 없습니다.")
						  )
						);
					}
					listBody.html(trTags);
					pagingArea.html(resp.pagingHTMLForBS);
				},
				error : function(errorResp) {
					console.log(errorResp.status + ", "
							+ errorResp.responseText)
				}
		});
	});
	
<!-- Menu Toggle Script -->
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
	
</script>

