<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

	<div class="container-fluid">
		<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header"><fmt:message key = "order.pageTitle.new" /></h1>

				<form id="order_add" name="order_form" action="${appNameAndLang}/${ ConstantValue.PAGE_ORDER_ADD_URI }" method="post" enctype="multipart/form-data">
					<msg:error errorCode="${ error_code }" msgList="${ empty_field }" 
							language="${currLanguage}" bean="order"
							itemTag="span" containerTag="div" containerClass="error_message"/>
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
									<td id="title">
										<input type="text" name="title" value="<c:out value="${title}" />" maxlength="250"/>
										<div><fmt:message key = "order.title.requirements" /></div>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.description" />*</td>
									<td id="description">
										<textarea name="description" cols="22" rows="5"><c:out value="${description}" /></textarea>
										<div><fmt:message key = "order.description.requirements" /></div>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.specification" />*</td>
									<td id="specification">
										<input type="file" name="specification" value="${specification}" />
										<div><fmt:message key = "order.specification.requirements" /></div>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateStart" />*</td>
									<td id="dateStart">
										<jsp:useBean id="now" class="java.util.Date"/> 
										<input type="date" name="dateStart" value="${dateStart}"  
											min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
										<div><fmt:message key = "order.dateStart.requirements" /></div>
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateFinish" />*</td>
									<td id="dateFinish">
										<input type="date" name="dateFinish" value="${dateFinish}" 
											min="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"/>
										<div><fmt:message key = "order.dateFinish.requirements" /></div>
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
					<input type="hidden" name="token" value="${ token }" /> 
					<input type="submit" class="btn btn-primary"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="<fmt:message key = "order.button.add" />" />
				</form>
			</div>
		</div>
	</div>
	


<%@include file="../jspf/footer.jsp"%>