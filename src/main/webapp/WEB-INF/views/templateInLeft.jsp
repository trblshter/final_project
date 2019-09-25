<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/bootstrap-4.3.1/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/fontawesome/css/all.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/bootstrap-4.3.1/js/bootstrap.bundle.js"></script>
<style type="text/css">
	#logo{font-size: 1.5rem; font-weight: 800;}
	#top,#footer {clear:both; width:100%;}
	#content{    width: calc(95% - 300px); float: right; margin-top: 80px;min-height: 719px;margin-right: 51px;}
	#left{ width: 300px; padding-right: 50px; position: absolute; height: calc(100% - 64px); top: 64px;}
	#left .list-group{ min-height: 800px; background:#efefef; height: 100%;}
	#left .list-group .list-group-item{background:none; border:0 none; color: #343a40; width: 100%;  height: 100%; display: block;}
	#left .list-group .list-group-item:hover{background:#44b1a7; color:#fff; font-weight:bold; border-radius:0;}
	#left .list-group .list-group-item .icon{display:inline-block; margin-right:12px;width: 20px;text-align: center;}
	#left .list-group .list-group-item .icon::before{ display: inline-block; font-style: normal; font-variant: normal; text-rendering: auto; -webkit-font-smoothing: antialiased;}
	#left .list-group .list-group-item .fas.fa-chevron-right {float: right; margin-top: 7px; font-size: 13px;}
	
	#left #appl .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f46d";}
	#left #payment .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f09d";}
	#left #myinfo .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f007";}
	#left #Withdrawal .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f52b";}
	#left #myBoardList .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f303";}
	#left #review .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f518";}
	#left #revenue .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f51e";}
	#left #pay .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f09d";}
	#left #appl .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f543";}
	#left #certification .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f4fc";}
	#left #blacklist .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f505";}
	#left #report .icon::before{ font-family: "Font Awesome 5 Free"; font-weight: 900; content: "\f4fa";}
	
	
	#top {height:64px;}
	
	.btn:hover{cursor:pointer;}
	#footer { background: #ddd; padding: 25px; z-index: 1;position: relative;}
	#footer .float-right { margin: 0;}
	#footer p { margin: 0;}
	body { padding-bottom: 0 !important; min-height:100%; position:relative;}
</style>

</head>
<body>
<div id="top">
	<tiles:insertAttribute name="header" />
</div>
<div id="left">
	<tiles:insertAttribute name="left" />
</div>
<div id="content">
 	<tiles:insertAttribute name="content"/>	
</div>
<div id="footer">
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html> 