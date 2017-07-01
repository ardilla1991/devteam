<%@include file="fragment/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<!-- h2 class="sub-header">Rented Equipment</h2 -->
			<div class="col-sm-5">
				<h1 class="page-header"><fmt:message key = "order.pageTitle.view" /></h1>
				<%@include file="fragment/orderView.jsp" %>
			</div>
			<div class="col-sm-7">
				<a class="btn btn-default" role="button" href="Main?action=project_show_add_form&order_id=${order_dto.getOrder().getId()}" id="project_add"><fmt:message key = "project.button.add" /></a>
				<div id="project_edit"></div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="fragment/footer.jsp"%>
