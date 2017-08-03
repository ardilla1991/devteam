<%@ taglib uri="messagetag" prefix="msg"%>
<div class="error_message">
	<c:if test="${ error_code > 0}">
		<msg:message errorCode="${ error_code }" language="${clientLanguage}"  
			itemTag="span" containerTag="div"
			country="${clientCountry}" bean="order" />
	</c:if>
</div>
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
				<td><c:out
						value="${project_vo.getProject().getOrder().getSpecification()}" /></td>
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
							<%@ taglib uri="messagetag" prefix="msg"%>
							<div class="error_message">
								<c:if test="${ error_code > 0}">
									<msg:message errorCode="${ error_code }"  itemTag="span" containerTag="div"
										msgList="${ empty_field }" language="${clientLanguage}"
										country="${clientCountry}" bean="project" />
								</c:if>
							</div>
							<input type="text" value="" name="hours" /> <input type="hidden"
								name="action" value="project_update_hours" /> 
							<input type="hidden" name="project_id"
								value="${project_vo.getProject().getId() }" /> 
							<input type="hidden" name="token" value="${ token }" />
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


