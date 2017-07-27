<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type">
<title>403</title>
</head>
<body>
	<c:set var="clientLanguage"
		value="${pageContext.request.locale.language}" scope="request" />
	<c:set var="clientCountry"
		value="${pageContext.request.locale.country}" scope="request" />

	<c:set var="curLocale" scope="request"
		value="${clientLanguage}_${clientCountry}" />

	<fmt:setLocale value="${curLocale}" />
	<fmt:setBundle basename="text" />
	<h1 class="page-header">
		<fmt:message key="error.403" />
	</h1>
</body>
</html>