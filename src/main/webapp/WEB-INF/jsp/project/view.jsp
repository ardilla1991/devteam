<%@include file="../fragment/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="../fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-12">
				<h1 class="page-header">
					<fmt:message key="project.pageTitle.view" />
				</h1>
				<%@include file="../fragment/projectView.jsp"%>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../fragment/footer.jsp"%>