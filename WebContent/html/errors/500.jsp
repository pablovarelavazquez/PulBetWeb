<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${sessionScope['user-locale']}" />
<fmt:setBundle basename="resources.Messages" var="messages"
	scope="session" />

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/web.css" />
<title>Error 500</title>
</head>

<body>
	<div class="green">
		<h2>
			<fmt:message key="error500h" bundle="${messages}" />
		</h2>
		<h3>
			<fmt:message key="error500p" bundle="${messages}" />
		</h3>
	</div>


</body>
</html>