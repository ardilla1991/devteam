<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="order.pageTitle.listOfNew" />
			</h1>

			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }" 
						language="${clientLanguage}" country="${clientCountry}"
						bean="order" />
				</c:if>
			</div>
			<c:set var="withCustomer" value="true" scope="page" />
			<%@include file="jspf/list.jsp"%>

			<ctg:paginator uri="Main?action=order_new_list"
				currPage="${ currPage }" countPages="${ countPages }" />
		</div>
	</div>
</div>
<%@include file="../jspf/footer.jsp"%>