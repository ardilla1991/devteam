<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<tbody>
			<tr>
				<td><fmt:message key = "order.title" /></td>
				<td><c:out value="${order_dto.getOrder().getTitle()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.description" /></td>
				<td><c:out value="${order_dto.getOrder().getDescription()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.specification" /></td>
				<td><c:out value="${order_dto.getOrder().getSpecification()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.dateStart" /></td>
				<td><c:out value="${order_dto.getOrder().getDateStart()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.dateFinish" /></td>
				<td><c:out value="${order_dto.getOrder().getDateFinish()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.works" /></td>
				<td><c:forEach items="${order_dto.getWorks()}" var="i">
						<c:out value="${i.getTitle()}" />
						<br />
					</c:forEach></td>
			</tr>
			<tr>
				<td><fmt:message key = "order.qualifications" /></td>
				<td id="qualification_list_count">
					<c:forEach items="${order_dto.getQualifications()}" var="i">
						<div data-id="${i.key.getId()}"
							 data-count="${i.value}" >
							<c:out value="${i.key.getTitle()}" />
							(<c:out value="${i.value}" />)
						</div>
					</c:forEach></td>
			</tr>
		</tbody>
	</table>
</div>


