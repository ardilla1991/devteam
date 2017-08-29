<%@ taglib uri="actiontag" prefix="acl"%>

<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<thead>
			<tr>
				<th><fmt:message key="action" /></th>
				<th>#</th>
				<th><fmt:message key="order.title" /></th>
				<th><fmt:message key="order.description" /></th>
				<th><fmt:message key="order.specification" /></th>
				<th><fmt:message key="order.dateCreated" /></th>
				<th><fmt:message key="order.dateStart" /></th>
				<th><fmt:message key="order.dateFinish" /></th>
				<th><fmt:message key="order.dateProcessing" /></th>
				<th><fmt:message key="order.price" /></th>
				<c:if test="${ withCustomer == true }">
					<th><fmt:message key="order.customerInformation" /></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${order_list}" var="i">
				<tr>
					<td>
						<fmt:message key="order.action.view" var="linkTitle"/>
						<acl:action user="${ user.getUser() }" 
						href="${ ConstantValue.PAGE_ORDER_VIEW_URI }${i.getId()}" 
						title="${ linkTitle }" 
						/>
					</td>
					<td><c:out value="${i.getId()}" /></td>
					<td><c:out value="${i.getTitle()}" /></td>
					<td><c:out value="${i.getDescription()}" /></td>
					<td><a href="${ appPath }${upload_path}${i.getSpecification()}" ><c:out value="${i.getSpecification()}" /></a></td>
					<td><fmt:formatDate value="${i.getDateCreated()}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td><c:out value="${i.getDateStart()}" /></td>
					<td><c:out value="${i.getDateFinish()}" /></td>
					<td><fmt:formatDate value="${i.getDateProcessing()}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td><c:out value="${i.getPrice()}" /></td>
					<c:if test="${ withCustomer == true }">
						<td><c:out value="${i.getCustomer().getName()}" />, <c:out
								value="${i.getCustomer().getEmail()}" />, <c:out
								value="${i.getCustomer().getPhone()}" /></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>