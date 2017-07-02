<%@include file="../fragment/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<div class="container-fluid">
	<div class="row">

		<%@include file="../fragment/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<th><fmt:message key = "action" /></th>
							<th>#</th>
							<th><fmt:message key = "project.title" /></th>
							<th><fmt:message key = "project.description" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${project_list}" var="i">
							<tr>
								<td><a href="Main?action=project_view&project_id=${i.getId()}"><fmt:message key = "order.action.view" /></a></td>
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


<%@include file="../fragment/footer.jsp"%>