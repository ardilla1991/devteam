<%@include file="fragment/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="fragment/leftCol.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<!-- h2 class="sub-header">Rented Equipment</h2 -->
			<div class="col-sm-5">
				<h1 class="page-header">View Order</h1>
				<div class="table-responsive">
					<table class="table table-striped tab-content tab-active">
						<tbody>
							<tr>
								<td>Title</td>
								<td><c:out value="${order.getOrder().getTitle()}" /></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><c:out value="${order.getOrder().getDescription()}" /></td>
							</tr>
							<tr>
								<td>Specification</td>
								<td><c:out value="${order.getOrder().getSpecification()}" /></td>
							</tr>
							<tr>
								<td>Date Start</td>
								<td><c:out value="${order.getOrder().getDateStart()}" /></td>
							</tr>
							<tr>
								<td>Date Finish</td>
								<td><c:out value="${order.getOrder().getDateFinish()}" /></td>
							</tr>
							<tr>
								<td>Works</td>
								<td><c:forEach items="${order.getWorks()}" var="i">
										<c:out value="${i.getTitle()}" />
										<br />
									</c:forEach></td>
							</tr>
							<tr>
								<td>Qualifications</td>
								<td><c:forEach items="${order.getQualifications()}" var="i">
										<c:out value="${i.key.getTitle()}" />
										<br />
									</c:forEach></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-sm-7">
				<a class="btn btn-default" role="button" href="#" id="project_add">Add project</a>
				<div id="project_edit"></div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="fragment/footer.jsp"%>
<script>
	ajaxActionListener("project_add", "project_edit", "Main?action=project_show_add_form&order_id=${order.getOrder().getId()}");
</script>