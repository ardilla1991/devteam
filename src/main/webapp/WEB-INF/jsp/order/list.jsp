<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="order.pageTitle.list" />
			</h1>
			<msg:error errorCode="${ error_code }" itemTag="span" containerTag="div"
						language="${currLanguage}" 
						bean="order" containerClass="error_message"/>
			<c:set var="withCustomer" value="false" scope="page" />
			<%@include file="jspf/list.jsp"%>
		</div>
	</div>
</div>
<%@include file="../jspf/footer.jsp"%>