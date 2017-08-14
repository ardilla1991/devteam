<%@ taglib uri="errormessagetag" prefix="msg"%>
<msg:error errorCode="${ error_code }" language="${clientLanguage}"  
			itemTag="span" containerTag="div"
			country="${clientCountry}" bean="order" containerClass="error_message" />

<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<tbody>
			<tr>
				<td><fmt:message key="project.title" /></td>
				<td><c:out value="${project_vo.getProject().getTitle()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="project.description" /></td>
				<td><c:out value="${project_vo.getProject().getDescription()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="project.dateCreated" /></td>
				<td><c:out value="${project_vo.getProject().getDateCreated()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.specification" /></td>
				<td><a
					href="${ appPath }${upload_path}${project_vo.getProject().getOrder().getSpecification()}"><c:out
							value="${project_vo.getProject().getOrder().getSpecification()}" /></a>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateStart" /></td>
				<td><c:out
						value="${project_vo.getProject().getOrder().getDateStart()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateFinish" /></td>
				<td><c:out
						value="${project_vo.getProject().getOrder().getDateFinish()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.customerInformation" /></td>
				<td><c:out
						value="${project_vo.getProject().getOrder().getCustomer().getName()}" />,
					<c:out
						value="${project_vo.getProject().getOrder().getCustomer().getEmail()}" />,
					<c:out
						value="${project_vo.getProject().getOrder().getCustomer().getPhone()}" />
				</td>
			</tr>
			<tr>
				<td><fmt:message key="project.employees" /></td>
				<td><c:forEach items="${project_vo.getEmployee()}" var="i">
						<div>
							<c:out value="${i.key.getName()}" />
							(
							<c:out value="${i.key.getQualification().getTitle()}" />
							) (
							<fmt:message key="project.hours" />
							:
							<c:out value="${i.value}" />
							)
						</div>
					</c:forEach></td>
			</tr>
		</tbody>
	</table>
</div>


