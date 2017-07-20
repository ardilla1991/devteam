<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type">
		<title><fmt:message key = "header.title.error" /></title>
	</head>
	<body>
		<c:out value="${error_msg}"/>
		<c:out value="error page"/>
	</body>
</html>