<%@include file="../fragment/header.jsp"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header"><fmt:message key = "order.pageTitle.listOfNew" /></h1>
			
			<c:set var="withCustomer" value="true" scope="page"/>
			<%@include file="../fragment/orderTable.jsp"%>
			
			<ctg:paginator uri="Main?action=order_new_list"
				currPage="${ currPage }" countPages="${ countPages }" />
		</div>
	</div>
</div>
<%@include file="../fragment/footer.jsp"%>