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
				<h1 class="page-header">View Order</h1>
				<!-- h2 class="sub-header">Rented Equipment</h2 -->
				<div class="table-responsive">
					<table class="table table-striped tab-content tab-active">
						<tbody>
							<tr>
								<td>Title</td>
								<td><c:out value="${order.getOrder().getTitle()}" /></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><c:out value="${order.getOrder().getDescription()}" /></td>
							</tr>
							<tr>
								<td>Specification</td>
								<td><c:out value="${order.getOrder().getSpecification()}" /></td>
							</tr>
							<tr>
								<td>Date Start</td>
								<td><c:out value="${order.getOrder().getDateStart()}" /></td>
							</tr>
							<tr>
								<td>Date Finish</td>
								<td><c:out value="${order.getOrder().getDateFinish()}" /></td>
							</tr>
							<tr>
								<td>Works</td>
								<td>
									<c:forEach items="${order.getWorks()}" var="i">
										<c:out value="${i.getTitle()}" />
										<br />
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td>Qualifications</td>
								<td>
									<c:forEach items="${order.getQualifications()}" var="i">
										<c:out value="${i.key.getTitle()}" />
										<br />
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>