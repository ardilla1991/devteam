
<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<thead>
			<tr>
				<th><fmt:message key="action" /></th>
				<th>#</th>
				<th><fmt:message key="project.title" /></th>
				<th><fmt:message key="project.description" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${project_list}" var="i">
				<tr>
					<td><a href="Main?action=project_view&project_id=${i.getId()}"><fmt:message
								key="project.action.view" /></a></td>
					<td><c:out value="${i.getId()}" /></td>
					<td><c:out value="${i.getTitle()}" /></td>
					<td><c:out value="${i.getDescription()}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>