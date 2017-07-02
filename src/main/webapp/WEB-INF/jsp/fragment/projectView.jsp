
<div class="table-responsive">
	<table class="table table-striped tab-content tab-active">
		<tbody>
			<tr>
				<td><fmt:message key="project.title" /></td>
				<td><c:out value="${project_dto.getProject().getTitle()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="project.description" /></td>
				<td><c:out value="${project_dto.getProject().getDescription()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.specification" /></td>
				<td><c:out
						value="${project_dto.getProject().getOrder().getSpecification()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateStart" /></td>
				<td><c:out
						value="${project_dto.getProject().getOrder().getDateStart()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.dateFinish" /></td>
				<td><c:out
						value="${project_dto.getProject().getOrder().getDateFinish()}" /></td>
			</tr>
			<tr>
				<td><fmt:message key="order.customerInformation" /></td>
				<td><c:out
						value="${project_dto.getProject().getOrder().getCustomer().getName()}" />,
					<c:out
						value="${project_dto.getProject().getOrder().getCustomer().getEmail()}" />,
					<c:out
						value="${project_dto.getProject().getOrder().getCustomer().getPhone()}" />
				</td>
			</tr>
			<tr>
				<td><fmt:message key="project.employees" /></td>
				<td><c:forEach items="${project_dto.getEmployee()}" var="i">
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
			<c:set var="role">DEVELOPER</c:set>
			<c:if test="${ user.getUser().getRole()  == role }">
				<tr>
					<td><fmt:message key="project.hours" /></td>
					<td id="hours">
						<form method="POST" action="Main">
							<script type="text/javascript">
								var formElements = {};
								formElements["hours"] = "number";
							</script>
							<input type="text" value="" name="hours" />
							<input type="hidden" name="action" value="project_update_hours" />
							<input type="hidden" name="project_id" value="${project_dto.getProject().getId() }" />
							<input type="submit" class="btn btn-primary"
								onclick="return checkFBForm(formElements);" name="submitted"
								value="<fmt:message key = "project.button.addHours" />" />
						</form>
					</td>
				</tr>
			</c:if>


		</tbody>
	</table>
</div>


