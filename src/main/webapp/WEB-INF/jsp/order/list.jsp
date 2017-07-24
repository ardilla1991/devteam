<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="order.pageTitle.list" />
			</h1>
			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }" itemTag="span" containerTag="div"
						language="${clientLanguage}" country="${clientCountry}"
						bean="order" />
				</c:if>
			</div>
			<c:set var="withCustomer" value="false" scope="page" />
			<%@include file="jspf/list.jsp"%>
		</div>
	</div>
</div>
<%@include file="../jspf/footer.jsp"%>