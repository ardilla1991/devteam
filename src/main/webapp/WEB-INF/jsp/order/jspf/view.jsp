
<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<tbody>
			<tr>
				<td><fmt:message key="order.title" /></td>
				<td><c:out value="${order_vo.getOrder().getTitle()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.description" /></td>
				<td><c:out value="${order_vo.getOrder().getDescription()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.specification" /></td>
				<td><a
					href="${ appPath }${upload_path}${order_vo.getOrder().getSpecification()}"><c:out
							value="${order_vo.getOrder().getSpecification()}" /></a></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateStart" /></td>
				<td><c:out value="${order_vo.getOrder().getDateStart()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateFinish" /></td>
				<td><c:out value="${order_vo.getOrder().getDateFinish()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateProcessing" /></td>
				<td><fmt:formatDate value="${order_vo.getOrder().getDateProcessing()}" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.work" /></td>
				<td><c:forEach items="${order_vo.getWorks()}" var="i">
						<c:out value="${i.getTitle()}" />
						<br />
					</c:forEach></td>
			</tr>
			<tr>
				<td><fmt:message key="order.qualification" /></td>
				<td id="qualification_list_count"><c:forEach
						items="${order_vo.getQualifications()}" var="i">
						<div data-id="${i.key.getId()}" data-count="${i.value}">
							<c:out value="${i.key.getTitle()}" />
							(
							<c:out value="${i.value}" />
							)
						</div>
					</c:forEach></td>
			</tr>
		</tbody>
	</table>
</div>


