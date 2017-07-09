<%@include file="../fragment/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="../fragment/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-5">
				<!-- jsp:include page="/Main?action=order_view&order_id=${order_id}&jspf=1"  flush="true" / -->
				<%@include file="../fragment/orderView.jsp"%>
				<div id="order_view"></div>
			</div>
			<div class="col-sm-7">
				<h1 class="page-header">
					<fmt:message key="project.pageTitle.new" />
				</h1>
				<!-- h2 class="sub-header">Rented Equipment</h2 -->
				<form id="order_add" name="order_form" action="Main" method="post">
					<div class="error_message">
						<c:if test="${ error_code > 0}">
							<msg:message errorCode="${ error_code }" msgList="${ empty_field }" language="${clientLanguage}" country="${clientCountry}" bean="project"/>
						</c:if>
					</div>
					<script type="text/javascript">
						var formElements = {};
						formElements["title"] = "text";
						formElements["description"] = "textarea";
						formElements["employee"] = "checkbox";
						formElements["price"] = "bigdecimal";
					</script>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td><fmt:message key="project.title" />*</td>
									<td id="title"><input type="text" name="title" maxlength="250"
										value="${title}" /></td>
								</tr>
								<tr>
									<td><fmt:message key="project.description" />*</td>
									<td id="description">
										<textarea name="description" cols="22" rows="5">${description}</textarea>
									</td>
								</tr>
								<tr>
									<td><fmt:message key="employee" />*</td>
									<td id="employee"><c:forEach items="${employee_list}"
											var="i">
											<div>

												<input type="checkbox" name="employee"
													<c:if test="${jpl:inArray(i.getId(), employee)}">checked="checked"</c:if>
													value="${i.getId()}"
													data-id="${i.getQualification().getId() }" />
												<c:out value="${i.getName()}" />
											</div>
										</c:forEach></td>
								</tr>
								<tr>
									<td><fmt:message key="order.price" />*</td>
									<td id="price"><input type="text" name="price"
										value="${price}" maxlength="11" /></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="el_obr_warn">
						<sup>*</sup> -
						<fmt:message key="required" />
					</div>
					<input type="hidden" name="order_id" value="${order_id}" /> 
					<input type="hidden" name="action" value="project_add" /> <input
						type="submit" class="btn btn-primary"
						onclick="return checkFBForm(formElements, 'qualification_list_count');"
						name="submitted"
						value="<fmt:message key = "project.button.add" />" />
				</form>

			</div>
		</div>
	</div>
</div>


<%@include file="../fragment/footer.jsp"%>
<!-- script>
	ajaxActionListener("order_view", "Main?action=order_view&order_id=${order_id}&jspf=1");
</script -->
