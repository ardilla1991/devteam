<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<tbody>
			<tr>
				<td>Title</td>
				<td><c:out value="${order_dto.getOrder().getTitle()}" /></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><c:out value="${order_dto.getOrder().getDescription()}" /></td>
			</tr>
			<tr>
				<td>Specification</td>
				<td><c:out value="${order_dto.getOrder().getSpecification()}" /></td>
			</tr>
			<tr>
				<td>Date Start</td>
				<td><c:out value="${order_dto.getOrder().getDateStart()}" /></td>
			</tr>
			<tr>
				<td>Date Finish</td>
				<td><c:out value="${order_dto.getOrder().getDateFinish()}" /></td>
			</tr>
			<tr>
				<td>Works</td>
				<td><c:forEach items="${order_dto.getWorks()}" var="i">
						<c:out value="${i.getTitle()}" />
						<br />
					</c:forEach></td>
			</tr>
			<tr>
				<td>Qualifications</td>
				<td><c:forEach items="${order_dto.getQualifications()}" var="i">
						<c:out value="${i.key.getTitle()}" />
										(<c:out value="${i.value}" />)<br />
					</c:forEach></td>
			</tr>
		</tbody>
	</table>
</div>


