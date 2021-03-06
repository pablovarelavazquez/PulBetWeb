<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.pulbet.web.util.* , com.pvv.pulbet.model.* , com.pulbet.web.controller.*"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope['user-locale']}"/>
<fmt:setBundle basename = "resources.Messages" var = "messages" scope="session"/>


<!DOCTYPE html>


<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/web.css" />
<script src="<%=request.getContextPath()%>/javascript/jquery-3.3.1.min.js" type="text/javascript"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/javascript/web.js"></script>

<link rel="icon" size="32x32" type="image/png" href="<%=request.getContextPath()%>/images/FaviconPulBet.png"/>

<title>PulBET - Para los amantes del deporte</title>

</head>
<c:set var="errors" scope="page" value ="${errors}"/>
<body>

	<div id="header">
	
		<div id="logo">
			<a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/images/LogoPulBet.png"></a>
		</div>
		
		<%@include file="/html/common/languages.jsp"%>

		<%@include file="/html/common/user-menu.jsp"%>

	</div>