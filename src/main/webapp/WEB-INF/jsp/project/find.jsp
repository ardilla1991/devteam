<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="project.pageTitle.find" />
			</h1>
			<div class="find_project_list">
				<%@include file="jspf/list.jsp"%>
			</div>
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>