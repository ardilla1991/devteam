<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="user.pageTitle.new" />
			</h1>

			<form name="user_form" action="${appNameAndLang}/${ ConstantValue.PAGE_USER_ADD_URI }${ employee_id }" method="post">
				<msg:error errorCode="${ error_code }"
							msgList="${ empty_field }" language="${currLanguage}"
							 bean="user" itemTag="span" 
							containerTag="div" containerClass="error_message"/>
				<script type="text/javascript">
					var formElements = {};
					formElements["login"] = "login";
					formElements["password"] = "password";
					formElements["role"] = "select";
				</script>
				<div class="table-responsive">
					<table class="table table-striped tab-content tab-active">
						<tbody>
							<tr>
								<td><fmt:message key="user.login" />*</td>
								<td id="login">
									<input type="text" name="login" value="${login}" maxlength="50" />
									<div><fmt:message key = "user.login.requirements" /></div>
								</td>
							</tr>
							<tr>
								<td><fmt:message key="user.password" />*</td>
								<td id="password">
									<input type="password" name="password" value="${password}" maxlength="50" />
									<div><fmt:message key = "user.password.requirements" /></div>
								</td>
							</tr>
							<tr>
								<%@ page import="by.htp.devteam.bean.UserRole"%>
								<td><fmt:message key="user.role" />*</td>
								<td id="role"><select name="role">
										<c:forEach items="${role_enum}" var="i">
											<c:if test="${ i != UserRole.CUSTOMER }">
												<option value="${i}">${i}</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div>
					<sup>*</sup> -
					<fmt:message key="required" />
				</div>
				<input type="hidden" name="token" value="${ token }" /> 
				<input
					type="submit" class="btn btn-primary"
					onclick="return checkFBForm(formElements);" name="submitted"
					value="<fmt:message key = "user.button.add" />" />
			</form>

		</div>
	</div>
</div>



<%@include file="../jspf/footer.jsp"%>