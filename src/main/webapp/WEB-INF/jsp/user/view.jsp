<%@include file="../jspf/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-12">
				<h1 class="page-header">
					<fmt:message key="user.pageTitle.view" />
				</h1>
				<div class="table-responsive">
					<table class="table table-striped tab-content tab-active">
						<tbody>
							<tr>
								<td><fmt:message key="user.login" /></td>
								<td><c:out value="${user_vo.getUser().getLogin()}" /></td>
							</tr>
							<tr>
								<td><fmt:message key="user.role" /></td>
								<td><c:out value="${user_vo.getUser().getRole()}" /></td>
							</tr>
							<%@ page import="by.htp.devteam.bean.UserRole" %>
							<c:choose>
								<c:when test="${ user_vo.getUser().getRole()  == UserRole.CUSTOMER }">
									<tr>
										<td><fmt:message key="user.name" /></td>
										<td><c:out
											value="${user_vo.getCustomer().getName()}" /></td>
									</tr>
									<tr>
										<td><fmt:message key="customer.email" /></td>
										<td><c:out
											value="${user_vo.getCustomer().getEmail()}" /></td>
									<tr>
										<td><fmt:message key="customer.phone" /></td>
										<td><c:out
											value="${user_vo.getCustomer().getPhone()}" /></td>
									</tr>
									
								</c:when>
								<c:otherwise >
									<tr>
										<td><fmt:message key="user.name" /></td>
										<td><c:out
											value="${user_vo.getEmployee().getName()}" /></td>
									</tr>
									<tr>
										<td><fmt:message key="employee.qualification" /></td>
										<td><c:out
											value="${user_vo.getEmployee().getQualification().getTitle()}" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../jspf/footer.jsp"%>