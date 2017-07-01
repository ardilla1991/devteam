<%@include file="fragment/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<a href="Main?action=order_show_add_form"><fmt:message key = "order.addNew" /></a>
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