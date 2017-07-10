<%@include file="../jspf/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-12">
				<h1 class="page-header">
					<fmt:message key="project.pageTitle.view" />
				</h1>
				<%@include file="jspf/view.jsp"%>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../jspf/footer.jsp"%>