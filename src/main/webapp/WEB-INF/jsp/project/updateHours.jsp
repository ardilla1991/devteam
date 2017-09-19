<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="actiontag" prefix="acl"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-12">
				<h1 class="page-header">
					<fmt:message key="project.pageTitle.view" />
				</h1>
				<%@include file="jspf/view.jsp"%>

				<table>
					<tr>
						<td><fmt:message key="project.hours" /></td>
						<td id="hours">
							<form method="POST" action="${appNameAndLang}/${ ConstantValue.PAGE_PROJECT_UPDATE_HOURS_URI }${project_vo.getProject().getId() }">
								<script type="text/javascript">
									var formElements = {};
									formElements["hours"] = "number";
								</script>
								<msg:error errorCode="${ error_code }" itemTag="span"
											containerTag="div" msgList="${ empty_field }"
											language="${currLanguage}" 
											bean="project" containerClass="error_message"/>

								<input type="text" value="${ hours }" name="hours" />
								<input type="hidden" name="token" value="${ token }" /> 
								<input
									type="submit" class="btn btn-primary"
									onclick="return checkFBForm(formElements);" name="submitted"
									value="<fmt:message key = "project.button.addHours" />" />
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../jspf/footer.jsp"%>