<%@include file="fragment/header.jsp"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="fragment/leftCol.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<a href="Main?action=order_show_add_form">Add new order</a>
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<th>Actions</th>
							<th>#</th>
							<th>Title</th>
							<th>Description</th>
							<th>Specification</th>
							<th>Date Created</th>
							<th>Project start date</th>
							<th>Project finish date</th>
							<th>Price</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${order_list}" var="i">
							<tr>
								<td><a href="Main?action=order_view&order_id=${i.getId()}">Look</a></td>
								<td><c:out value="${i.getId()}" /></td>
								<td><c:out value="${i.getTitle()}" /></td>
								<td><c:out value="${i.getDescription()}" /></td>
								<td><c:out value="${i.getSpecification()}" /></td>
								<td><c:out value="${i.getDateCreated()}" /></td>
								<td><c:out value="${i.getDateStart()}" /></td>
								<td><c:out value="${i.getDateFinish()}" /></td>
								<td><c:out value="${i.getPrice()}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>


<%@include file="fragment/footer.jsp"%>