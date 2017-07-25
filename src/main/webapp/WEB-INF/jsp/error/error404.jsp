<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isErrorPage="true" %>
<c:set var="page_title" value="404" scope="request" />
<%@include file="../jspf/header.jsp"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header"><fmt:message key="error.404" /></h1>
			
		</div>
	</div>
</div>

<%@include file="../jspf/footer.jsp"%>