<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib uri="messagetag" prefix="msg"%>

	<div class="container-fluid">
		<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header"><fmt:message key = "user.pageTitle.new" /></h1>

				<form id="order_add" name="order_form" action="Main?action=user_add_employee" method="post" enctype="multipart/form-data">
					<div class="error_message">
						<c:if test="${ error_code > 0}">
							<msg:message errorCode="${ error_code }" msgList="${ empty_field }" 
									 	 language="${clientLanguage}" country="${clientCountry}" 
									 	 bean="order" itemTag="span" containerTag="div"/>
						</c:if>
					</div>
					<script type="text/javascript">
						var formElements = {};
						formElements["name"] = "text";
						formElements["startWork"] = "date";
						formElements["qualification"] = "select";
						formElements["login"] = "text";
						formElements["password"] = "text";
						formElements["role"] = "select";
					</script>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td><fmt:message key = "user.name" />*</td>
									<td id="name"><input type="text" name="name" value="${name}" maxlength="50"/></td>
								</tr>
								<tr>
									<td><fmt:message key = "employee.startWork" />*</td>
									<td id="startWork">
										<jsp:useBean id="now" class="java.util.Date"/> 
										<input type="date" name="startWork" value="${startWork}" value="${now}" pattern="yyyy-MM-dd"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "qualification" />*</td>
									<td id="qualification">
										<select name="qualification">
											<c:forEach items="${qualification_list}" var="i">
												<option value="${i.getId()}">${i.getTitle()}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "user.login" />*</td>
									<td id="login">
										<input type="text" name="login" value="${login}" maxlength="250"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "user.password" />*</td>
									<td id="password">
										<input type="text" name="password" value="${password}" maxlength="10"/>
									</td>
								</tr>
								<tr>
									<%@ page import="by.htp.devteam.bean.UserRole" %>
									<td><fmt:message key = "user.role" />*</td>
									<td id="role">
										<select name="role">
											<c:forEach items="${role_enum}" var="i">
												<c:if test="${ i != UserRole.CUSTOMER }">
													<option value="${i}">${i}</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="el_obr_warn">
						<sup>*</sup> - <fmt:message key = "required" />
					</div>
					<input type="hidden" name="action" value="user_add_employee" /> 
					<input type="submit" class="btn btn-primary"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="<fmt:message key = "user.button.add" />" />
				</form>
			</div>
		</div>
	</div>
	


<%@include file="../jspf/footer.jsp"%>