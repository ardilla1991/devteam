<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="user.pageTitle.list" />
			</h1>
			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }"
						language="${clientLanguage}" country="${clientCountry}"
						bean="project" />
				</c:if>
			</div>
			<div class="table-responsive">
				<table class="table table-striped tab-content tab-active">
					<thead>
						<tr>
							<!--th><fmt:message key="action" /></th -->
							<th>#</th>
							<th><fmt:message key="user.login" /></th>
							<th><fmt:message key="user.role" /></th>
							<th><fmt:message key="user.name" /></th>
							<th><fmt:message key="employee.qualification" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="user_list" value="${user_list_vo.getUsers()}" scope="page" />
						<%@ page import="by.htp.devteam.bean.UserRole" %>
						<c:forEach items="${user_list}" var="i">
							<tr>
								<!--td><a
									href="Main?action=project_view&project_id=${i.getUser().getId()}"><fmt:message
											key="project.action.view" /></a></td -->
								<td><c:out value="${i.getUser().getId()}" /></td>
								<td><c:out value="${i.getUser().getLogin()}" /></td>
								<td><c:out value="${i.getUser().getRole()}" /></td>
								<c:choose>
									<c:when test="${ i.getUser().getRole()  == UserRole.CUSTOMER }">
										<td><c:out value="${i.getCustomer().getName()}" /></td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${i.getEmployee().getName()}" /></td>
										<td><c:out value="${i.getEmployee().getQualification().getTitle()}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<ctg:paginator uri="${ uri }"
				currPage="${ user_list_vo.getCurrPage() }"
				countPages="${ user_list_vo.getCountPages() }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>