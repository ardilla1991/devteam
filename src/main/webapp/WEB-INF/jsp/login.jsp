<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login page</title>
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/style.css">
	</head>
	<body class="login">
	
		<c:set var="clientLanguage"
			value="${pageContext.request.locale.language}" />
		<c:set var="clientCountry"
			value="${pageContext.request.locale.country}" />
	
		<fmt:setLocale value="${clientLanguage}_${clientCountry}" />
		<fmt:setBundle basename="text" />
	
	
	
		<div class="container">
			<form class="form-signin" role="form" action="Main" method="POST">
				<h2 class="form-signin-heading">
					<fmt:message key="login.formTitle" />
				</h2>
				<input type="text" class="form-control"
					placeholder="<fmt:message key = "user.login" />" required autofocus
					name="login" value="${login}" /> 
				<input type="password" class="form-control"
					placeholder="<fmt:message key = "user.password" />" required
					name="pass" value="${password}" />
				<div class="error_message">
					<c:if test="${ error_code > 0}">
						<msg:message errorCode="${ error_code }" msgList="${ empty_field }"
							language="${clientLanguage}" country="${clientCountry}"
							bean="user" />
					</c:if>
				</div>
				<input type="hidden" name="action" value="login" />
				<button class="btn btn-lg btn-primary btn-block" type="submit">
					<fmt:message key="login.signin" />
				</button>
			</form>
		</div>
	
	</body>
</html>