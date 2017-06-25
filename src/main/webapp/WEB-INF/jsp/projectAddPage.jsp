<%@include file="fragment/header.jsp"%>
	<div class="container-fluid">
		<div class="row">
		<%@include file="fragment/leftCol.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">New Project</h1>

				<!-- h2 class="sub-header">Rented Equipment</h2 -->
				<form id="order_add" name="order_form" action="Main" method="post">
					<script type="text/javascript">
						var formElements = {};
						formElements["title"] = "text";
						formElements["description"] = "text";
					</script>
					<div class="table-responsive">
						<table class="table table-striped tab-content tab-active">
							<tbody>
								<tr>
									<td>Title</td>
									<td id="title"><input type="text" name="title" value="${i.getTitle()}" /></td>
								</tr>
								<tr>
									<td>Description</td>
									<td id="description">
										<input type="text" name="description" value="${i.getDescription()}" />
									</td>
								</tr>
								<tr>
									<td>Works</td>
									<td id="work">
										<c:forEach items="${work_list}" var="i">
											<input type="checkbox" name="work" value="${i.getId()}" />
											<c:out value="${i.getTitle()}" />
											<br />
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td>Qualifications</td>
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
						<sup>*</sup> - required
					</div>
					<input type="hidden" name="order_id" value="${order_id} }" />
					<input type="hidden" name="action" value="project_add" /> 
					<input type="submit" class="form_submit"
						onclick="return checkFBForm(formElements);" name="submitted"
						value="Send" />
				</form>
			</div>
		</div>
	</div>
	


<%@include file="fragment/footer.jsp"%>