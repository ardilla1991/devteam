<%@include file="fragment/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header"><fmt:message key = "order.pageTitle.listOfNew" /></h1>
			<!-- h2 class="sub-header">Rented Equipment</h2 -->
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<th><fmt:message key = "action" /></th>
							<th>#</th>
							<th><fmt:message key = "order.title" /></th>
							<th><fmt:message key = "order.description" /></th>
							<th><fmt:message key = "order.specification" /></th>
							<th><fmt:message key = "order.dateCreated" /></th>
							<th><fmt:message key = "order.dateStart" /></th>
							<th><fmt:message key = "order.dateFinish" /></th>
							<th><fmt:message key = "order.price" /></th>
							<th><fmt:message key = "order.customerInformation" /></th>
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
								<td><c:out value="${i.getCustomer().getName()}" />, <c:out
										value="${i.getCustomer().getEmail()}" />, <c:out
										value="${i.getCustomer().getPhone()}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- ctg:paginator countPages="${ user }"/ -->
			<ctg:paginator uri="Main?action=order_new_list"
				currPage="${ currPage }" countPages="${ countPages }" />
		</div>
	</div>
</div>
<%@include file="fragment/footer.jsp"%>