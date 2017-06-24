<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="pagetags" prefix="ctg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>page</title>
	</head>
	<body>
	<a href="Main?action=logout">Exit</a>
		<div class="container-fluid">
			<div class="row">
	
	
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">New Order</h1>
	
					<!-- h2 class="sub-header">Rented Equipment</h2 -->
					<form id="order_add" name="order_form"
						action="Main" method="post">
						<script type="text/javascript">
							var formElements = {};
						</script>
						<div class="table-responsive">
							<table class="table table-striped tab-content tab-active">
								<tbody>
									<tr>
										<td>Title</td>
										<td id="title"><input type="text" name="title"
											value="${i.getTitle()}" /></td>
										<script type="text/javascript">
											formElements["title"] = "text";
										</script>
									</tr>
									<tr>
										<td>Description</td>
										<td id="description"><input type="text" name="description"
											value="${i.getDescription()}" /></td>
									</tr>
									<tr>
										<td>Specification</td>
										<td id="specification"><input type="text"
											name="specification" value="${i.getSpecification()}" /></td>
										<script type="text/javascript">
											formElements["specification"] = "file";
										</script>
									</tr>
									<tr>
										<td>Date Start</td>
										<td id="dateStart"><input type="text" name="dateStart"
											value="${i.getDateStart()}" /></td>
									</tr>
									<tr>
										<td>Date Finish</td>
										<td id="dateFinish"><input type="text" name="dateFinish"
											value="${i.getDateFinish()}" /></td>
									</tr>
									<tr>
										<td>Works</td>
										<td id="work">
											<c:forEach items="${work_list}" var="i">
												<input type="checkbox" name="work" value="${i.getId()}" />
												<c:out value="${i.getTitle()}" />
												<br />
											</c:forEach> 
											<script type="text/javascript">
												formElements["work"] = "checkbox";
											</script></td>
									</tr>
									<tr>
										<td>Qualifications</td>
										<td id="qualification"><c:forEach
												items="${qualification_list}" var="i">
												<input type="text" name="qualification[${i.getId()}]" value="" />
												<c:out value="${i.getTitle()}" />
												<br />
											</c:forEach> <script type="text/javascript">
												formElements["qualification"] = "text_group";
											</script></td>
									</tr>
								</tbody>
							</table>
						</div>
						<input type="hidden" name="id" value="${order.getId()}" />
						<div class="el_obr_warn">
							<sup>*</sup> - required
						</div>
						<input type="hidden" name="action" value="order_add" />
						<input type="submit" class="form_submit"
							onclick="return checkFBForm(formElements);" name="submitted"
							value="Send" />
					</form>
				</div>
			</div>
		</div>
	
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"
			integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
			crossorigin="anonymous"></script>
		<script src="js/scripts.js"></script>
		<!-- script>
			$('input[type="checkbox"]').change(function(){
			    this.value ^= 1;
			});
		</script -->
	
	</body>
</html>