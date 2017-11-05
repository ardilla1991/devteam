<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="actiontag" prefix="acl"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-12">
				<h1 class="page-header">
					<fmt:message key="project.pageTitle.view" />
				</h1>
				<%@include file="jspf/view.jsp"%>
				
				<c:if test="${ project_vo.getProject().getId() > 0}">
					<fmt:message key="project.button.addHours" var="linkTitle"/>
					<acl:action user="${ user.getUser() }" 
							href="${appNameAndLang}/${ ConstantValue.PAGE_PROJECT_UPDATE_HOURS_URI }${project_vo.getProject().getId() }" 
							title="${ linkTitle }" 
							className="btn btn-default" buttonRole="button" 
							id="project_add" />
					<div id="project_edit"></div>
				</c:if>
				
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../jspf/footer.jsp"%>