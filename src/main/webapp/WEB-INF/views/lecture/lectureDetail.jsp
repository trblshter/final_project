<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="cPath" value="${pageContext.request.contextPath }"></c:set>

<link href="${cPath}/bootstrap-4.3.1/css/bootstrap.min.css" rel="stylesheet">
<link href="${cPath}/css/simple-sidebar.css" rel="stylesheet">

<!-- calendar css import -->
<link rel=" shortcut icon" href="image/favicon.ico">

<link rel="stylesheet" href="<c:url value='/vendor/css/fullcalendar.min.css'/>" />
<%-- <link rel="stylesheet" href="<c:url value='/vendor/css/bootstrap.min.css'/>"> --%>
<link rel="stylesheet" href='<c:url value="/vendor/css/select2.min.css"/>' />
<link rel="stylesheet" href='<c:url value="/vendor/css/bootstrap-datetimepicker.min.css"/>'/>

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">

<link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>

<style>
	div.side-one {
		width: 10%;
	}
</style>
<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
	<ul class="nav nav-pills justify-content-center mt-2">
	  <li class="nav-item">
	    <a id="list" value="list" class="nav-link active" href="<c:url value='/myLecture/list'/>">나의 강의 목록</a>
	  </li>
	  <li class="nav-item">
	    <a id="timeTable" value="calendar" class="nav-link" href="<c:url value='/myLecture/calendar'/>">나의 강의 현황</a>
	  </li>
	</ul>
</nav>
<div class="d-flex" id="wrapper">
	
	<!-- Sidebar -->
	<div class="bg-light border-right side-one" id="sidebar-wrapper" style="width : 240px;">
		<div class="sidebar-heading" style="width: 240px; font-size: 19.2px; padding: 14px 20px 14px 20px">
			<h4 class="text-primary">${lecture.lt_title}</h4>
			<p class="mb-0">${lecture.lt_writer}</p>
		</div>
		<div class="list-group list-group-flush" style="width: 100%;">
			<button class="list-group-item list-group-item-action bg-light" id="infoBtn" value="info"><i class="fas fa-check">&nbsp;나의 강의 정보</i></button>
			<button class="list-group-item list-group-item-action bg-light" id="rptBtn" value="report"><i class="fas fa-check">&nbsp;나의 과제</i></button>
		</div>
	</div>
	<!-- /#sidebar-wrapper -->

	<!-- Page Content -->
	<div id="page-content-wrapper">
		<button class="btn btn-outline-dark rounded-0 border-0 position-absolute" id="menu-toggle"><i class="fas fa-bars"></i></button>
		<div id="lectureDetailFrag">
			<c:if test="${category eq 'info' }">
				<%@include file="/WEB-INF/views/lecture/subPages/lectureInfo.ejs" %>
			</c:if>
			<c:if test="${category eq 'report' }">
				<%@include file="/WEB-INF/views/lecture/subPages/myHomework.ejs" %>
			</c:if>
		</div>
	</div>
</div>

<form id="savedlecture">
	<input type="hidden" name="lecNo" value="${lt_no }"></input>
</form>

<!-- Bootstrap core JavaScript -->
<script src="${cPath}/js/jquery.min.3.4.1.js"></script>
<script src="${cPath}/bootstrap-4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
	
	var infoBtn = $("button#infoBtn");
	var rptBtn = $("button#rptBtn");
	var calendarBtn = $("button#calendarBtn");
	var lecNo = $("input[name=lecNo]").val();
	
	infoBtn.on("click",function(){
		location.href="${cPath}/myLecture/detail/"+lecNo;
	})
	
	calendarBtn.on("click",function(){
		location.href="${cPath}/myLecture/detail/calendar/"+lecNo;
	})
	
	rptBtn.on("click",function(){
		location.href="${cPath}/myLecture/detail/homework/"+lecNo;
	})
	
// 	$('tbody#listBody').on('click', 'a', function(){
// 		var what = $(this).prop('id');
// 		location.href = "${cPath}/lecture/lectureView.do?what=" + what;
// 		console.log(what);
// 	}).css({cursor : "pointer"});
	
	var head = $("#head");
	var titleBar = $('#titleBar');
	var pagingArea = $("#pagingArea");
	var what;
	
	
// <!-- Menu Toggle Script -->
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
	
</script>

