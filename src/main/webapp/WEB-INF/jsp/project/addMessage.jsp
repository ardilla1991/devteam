<%@include file="../fragment/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../fragment/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="project.pageTitle.new" />
			</h1>
			<div>
				<fmt:message key="project.addOk" />
			</div>

		</div>
	</div>
</div>


<%@include file="../fragment/footer.jsp"%>