<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/bootstrap-4.3.1/js/bootstrap.bundle.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/bootstrap-4.3.1/css/bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/fontawesome/css/all.css"/>

</head>

<div id="content">
 	<tiles:insertAttribute name="content"/>	
</div>

</body>
</html> 