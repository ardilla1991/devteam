<%@include file="../fragment/header.jsp"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header"><fmt:message key = "order.pageTitle.list" /></h1>
			<c:set var="withCustomer" value="false" scope="page"/>
			<%@include file="../fragment/orderTable.jsp"%>
		</div>
	</div>
</div>
<%@include file="../fragment/footer.jsp"%>