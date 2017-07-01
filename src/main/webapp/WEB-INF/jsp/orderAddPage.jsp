<%@include file="fragment/header.jsp"%>
<%@ taglib uri="/WEB-INF/tld/jspPlugin.tld" prefix="jpl"%>
	<div class="container-fluid">
		<div class="row">
		<%@include file="fragment/leftBar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header"><fmt:message key = "order.pageTitle.new" /></h1>

				<!-- h2 class="sub-header">Rented Equipment</h2 -->
				<form id="order_add" name="order_form" action="Main" method="post">
					<div><c:out value="${error_message}" /></div>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td><fmt:message key = "order.title" /></td>
									<td id="title"><input type="text" name="title" value="${title}" /></td>
								</tr>
								<tr>
									<td><fmt:message key = "order.description" /></td>
									<td id="description">
										<input type="text" name="description" value="${description}" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.specification" /></td>
									<td id="specification">
										<input type="text" name="specification" value="${specification}" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateStart" /></td>
									<td id="dateStart">
										<input type="text" name="dateStart" value="${dateStart}" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.dateFinish" /></td>
									<td id="dateFinish">
										<input type="text" name="dateFinish" value="${dateFinish}" />
									</td>
								</tr>
								<tr>
									<td><fmt:message key = "order.works" /></td>
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
									<td><fmt:message key = "order.qualifications" /></td>
									<td id="qualification">
										<c:forEach
											items="${qualification_list}" var="i">
											<input type="text" name="qualification[${i.getId()}]"
												value="" />
											<c:out value="${i.getTitle()}" />
											<br />
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
					<script type="text/javascript">
						var formElements = {};
						formElements["title"] = "text";
						formElements["dateStart"] = "text";
						formElements["dateFinish"] = "text";
						//formElements["specification"] = "file";
						formElements["work"] = "checkbox";
						formElements["qualification"] = "text_group";
					</script>
					<input type="hidden" name="action" value="order_add" /> <input
						type="submit" class="form_submit"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="<fmt:message key = "order.button.add" />" />
				</form>
			</div>
		</div>
	</div>
	


<%@include file="fragment/footer.jsp"%>