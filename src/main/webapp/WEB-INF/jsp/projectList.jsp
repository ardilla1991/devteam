<%@include file="fragment/header.jsp"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="fragment/leftCol.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<th>Actions</th>
							<th>#</th>
							<th>Title</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${project_list}" var="i">
							<tr>
								<td><a href="Main?action=order_view&order_id=${i.getId()}">Look</a></td>
								<td><c:out value="${i.getId()}" /></td>
								<td><c:out value="${i.getTitle()}" /></td>
								<td><c:out value="${i.getDescription()}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>


<%@include file="fragment/footer.jsp"%>