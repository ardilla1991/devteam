<%@include file="fragment/header.jsp"%>
<div class="container-fluid">
	<div class="row">
		<%@include file="fragment/leftCol.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			
			<div class="col-sm-5">
				<!-- jsp:include page="/Main?action=order_view&order_id=${order_id}&jspf=1"  flush="true" / -->
				<%@include file="fragment/orderView.jsp" %>
				<div id="order_view"></div>
			</div>
			<div class="col-sm-7">
				<h1 class="page-header">New Project</h1>
				<!-- h2 class="sub-header">Rented Equipment</h2 -->
				<form id="order_add" name="order_form" action="Main" method="post">
				<div>error!!!!!!<c:out value="${error_message}" /></div>
					<script type="text/javascript">
						var formElements = {};
						formElements["title"] = "text";
						formElements["description"] = "text";
						formElements["employee"] = "checkbox";
					</script>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td>Title</td>
									<td id="title"><input type="text" name="title" value="${title}" /></td>
								</tr>
								<tr>
									<td>Description</td>
									<td id="description"><input type="text" name="description" value="${description}" /></td>
								</tr>
								<tr>
									<td>Employee</td>
									<td id="employee">
										<c:forEach
											items="${employee_list}" var="i">
											<input type="checkbox" name="employee" value="${i.getId()}" />
											<c:out value="${i.getName()}" />
											<br />
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<input type="hidden" name="order_id" value="${order_id}" />
					<div class="el_obr_warn">
						<sup>*</sup> - required
					</div>
					<input type="hidden" name="order_id" value="${order_id}" /> <input
						type="hidden" name="action" value="project_add" /> <input
						type="submit" class="form_submit"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="Send" />
				</form>
			</div>
		</div>
	</div>
</div>


<%@include file="fragment/footer.jsp"%>
<!-- script>
	ajaxActionListener("order_view", "Main?action=order_view&order_id=${order_id}&jspf=1");
</script -->
