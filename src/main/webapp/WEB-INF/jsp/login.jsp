<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

<fmt:setLocale value="${currLanguage}" />
<fmt:setBundle basename="text" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><fmt:message key = "header.title.login" /></title>
		<link href="${ appPath }bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ appPath }style/style.css">
	</head>
	<body class="login">
		<%@ page import="by.htp.devteam.controller.util.ConstantValue" %>
		<div class="container">
			<form class="form-signin" role="form" action="${appNameAndLang}/${ ConstantValue.PAGE_USER_LOGIN_URI }" method="POST">
				<h2 class="form-signin-heading">
					<fmt:message key="login.formTitle" />
				</h2>
				<script type="text/javascript">
					var formElements = {};
					formElements["login"] = "text";
					formElements["password"] = "password";
				</script>
				<div id="login"><input type="text" class="form-control" maxlength="50"
					placeholder="<fmt:message key = "user.login" />" required autofocus
					name="login" value="<c:out value="${login}" />" /> </div>
				<div id="password"><input type="password" class="form-control" maxlength="50" 
					placeholder="<fmt:message key = "user.password" />" required
					name="pass" value="<c:out value="${password}" />" /></div>
				<div class="error_message">
					<c:if test="${ error_code > 0}">
						<msg:error errorCode="${ error_code }" msgList="${ empty_field }"
							language="${currLanguage}" itemTag="span" containerTag="div"
							bean="user" />
					</c:if>
				</div>
				<button class="btn btn-lg btn-primary btn-block login" 
						onclick="return checkFBForm(formElements);"
						type="submit">
					<fmt:message key="login.signin" />
				</button>
			</form>
		</div>
		<script src="${ appPath }js/script.js"></script>	
	</body>
</html>