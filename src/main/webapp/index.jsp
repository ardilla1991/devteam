<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="menutag" prefix="tmt"%>

<c:set var="clientLanguage" value="${pageContext.request.locale.language}" scope="request"/>
<c:set var="clientCountry" value="${pageContext.request.locale.country}" scope="request"/>

<c:set var = "curLocale" scope = "request" value = "${clientLanguage}_${clientCountry}"/>

<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="text"/>
<html>
	<head>
		<meta http-equiv="Content-Type">
		<title>DevTeam</title>
		<!-- script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>   -->
		<!-- script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>   -->
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="style/style.css">
	</head>
	<body>
		<div class="welcome-page">
			<div class="menu">
		        <div class="container">
		            <a class="site-title" href="#">DevTeam</a>
		            <%@ page import="by.htp.devteam.controller.util.ConstantValue" %>
		            <a class="site-menu" href="${ ConstantValue.PAGE_USER_LOGIN_URI }"><fmt:message key = "welcome.enter" /></a>
		            <!-- /.navbar-collapse -->
		        </div>
		        <!-- /.container-fluid -->
		    </div>
		    <div class="welcom-text"><fmt:message key = "welcome.text" /></div>
			<div class="big-img"><img src="image/website-build.jpg" /></div>
		</div>
	<div class="copyright"><div class="container"><div class="text"><fmt:message key = "welcome.copyright" /></div></div></div>
	</body>
</html>