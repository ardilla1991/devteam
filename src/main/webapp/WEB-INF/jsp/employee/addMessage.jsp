<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="employee.pageTitle.new" />
			</h1>
			<div>
				<fmt:message key="employee.addOk" />
			</div>
			<div>
				<%@include file="../user/jspf/add.jsp"%>
			</div>
		</div>
	</div>
</div>



<%@include file="../jspf/footer.jsp"%>