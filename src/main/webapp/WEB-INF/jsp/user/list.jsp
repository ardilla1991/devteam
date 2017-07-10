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
			<c:set var="project_list" value="${project_list_vo.getProjects()}"
				scope="request" />
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
								<td><a
									href="Main?action=project_view&project_id=${i.getId()}"><fmt:message
											key="project.action.view" /></a></td>
								<td><c:out value="${i.getId()}" /></td>
								<td><c:out value="${i.getTitle()}" /></td>
								<td><c:out value="${i.getDescription()}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<ctg:paginator uri="${ uri }"
				currPage="${ project_list_vo.getCurrPage() }"
				countPages="${ project_list_vo.getCountPages() }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>