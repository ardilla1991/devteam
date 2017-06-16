
<%@include file="fragment/header.jsp"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="fragment/leftCol.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">New Orders</h1>

			<!-- h2 class="sub-header">Rented Equipment</h2 -->
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<th>Actions</th>
							<th>#</th>
							<th>Title</th>
							<th>Description</th>
							<th>Date Created</th>
							<th>Project start date</th>
							<th>Project finish date</th>
							<th>Customer information</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${order_list}" var="i">
							<tr>
								<td><a
									href="Admin?action=project_edit&project_id=${i.getId()}">Edit</a></td>
								<td><c:out value="${i.getId()}" /></td>
								<td><c:out value="${i.getTitle()}" /></td>
								<td><c:out value="${i.getDescription()}" /></td>
								<td><c:out value="${i.getDateCreated()}" /></td>
								<td><c:out value="${i.getDateStart()}" /></td>
								<td><c:out value="${i.getDateFinish()}" /></td>
								<td><c:out value="${i.getCustomer().getName()}" />, <c:out
										value="${i.getCustomer().getEmail()}" />, <c:out
										value="${i.getCustomer().getPhone()}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<!-- ctg:paginator countPages="${ user }"/ -->
			<ctg:paginator uri="Main?action=admin_orders_new_list"
				currPage="${ currPage }" countPages="${ countPages }" />

		</div>
	</div>
</div>

<%@include file="fragment/footer.jsp"%>