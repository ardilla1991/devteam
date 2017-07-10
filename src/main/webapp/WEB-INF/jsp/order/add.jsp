<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib uri="messagetag" prefix="msg"%>

	<div class="container-fluid">
		<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header"><fmt:message key = "order.pageTitle.new" /></h1>

				<form id="order_add" name="order_form" action="Main?action=order_add" method="post" enctype="multipart/form-data">
					<div class="error_message">
						<c:if test="${ error_code > 0}">
							<msg:message errorCode="${ error_code }" msgList="${ empty_field }" language="${clientLanguage}" country="${clientCountry}" bean="order"/>
						</c:if>
					</div>
					<script type="text/javascript">
						var formElements = {};
						formElements["title"] = "text";
						formElements["description"] = "textarea";
						formElements["dateStart"] = "date";
						formElements["dateFinish"] = "date";
						formElements["specification"] = "file";
						formElements["work"] = "checkbox";
						formElements["qualification"] = "text_group";
					</script>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td><fmt:message key = "order.title" />*</td>
									<td id="title"><input type="text" name="title" value="${title}" maxlength="250"/></td>
								</tr>
								<tr>
									<td><fmt:message key = "order.description" />*</td>
									<td id="description">
										<textarea name="description" cols="22" rows="5">${description}</textarea>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.specification" />*</td>
									<td id="specification">
										<input type="file" name="specification" value="${specification}" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateStart" />*</td>
									<td id="dateStart">
										<jsp:useBean id="now" class="java.util.Date"/> 
										<input type="date" name="dateStart" value="${dateStart}"  
											min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateFinish" />*</td>
									<td id="dateFinish">
										<input type="date" name="dateFinish" value="${dateFinish}" 
											min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.work" />*</td>
									<td id="work">
										<c:forEach items="${work_list}" var="i">
											<div>
												<input type="checkbox" name="work"  <c:if test="${jpl:inArray(i.getId(), work)}">checked="checked"</c:if>
													  value="${i.getId()}"/>
												 <c:out value="${i.getTitle()}" />
											</div>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.qualification" />*</td>
									<td id="qualification">
										<c:forEach items="${qualification_list}" var="i">
											<div>
												<c:set var="q_id">${i.getId()}</c:set>
												<input type="text" name="qualification[${q_id}]"
													value="${qualification[q_id]}" />
												<c:out value="${i.getTitle()}" />
											</div>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<input type="hidden" name="id" value="${order.getId()}" />
					<div class="el_obr_warn">
						<sup>*</sup> - <fmt:message key = "required" />
					</div>
					<input type="hidden" name="action" value="order_add" /> 
					<input type="submit" class="btn btn-primary"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="<fmt:message key = "order.button.add" />" />
				</form>
			</div>
		</div>
	</div>
	


<%@include file="../jspf/footer.jsp"%>