<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type">
<title>${ param.errorCode }</title>
</head>
<body>

	<fmt:setLocale value="${currLanguage}" />
	<fmt:setBundle basename="text" />
	<h1 class="page-header">
		<fmt:message key="error.${ param.errorCode }" />
	</h1>
	<fmt:message key="gohome" var="gohome"/>
	<%@ page import="by.htp.devteam.controller.util.ConstantValue" %>
	<a href="${ ConstantValue.URL_DELIMITER }${ ConstantValue.APP_NAME }" title="${ gohome }" >${ gohome }</a>
</body>
</html>