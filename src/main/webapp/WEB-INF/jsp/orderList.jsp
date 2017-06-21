<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login page</title>
		<!-- script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>   -->
		<!-- script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>   -->
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/styles.css">
	</head>
<body>
<a href="Main?action=order_show_add_form">Add new order</a>
	<div class="table-responsive">
		<table class="table table-striped tab-content tab-active">
			<thead>
				<tr>
					<th>Actions</th>
					<th>#</th>
					<th>Title</th>
					<th>Description</th>
					<th>Specification</th>
					<th>Date Created</th>
					<th>Project start date</th>
					<th>Project finish date</th>
					<th>Price</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${order_list}" var="i">
					<tr>
						<td><a
							href="Main?action=order_look&order_id=${i.getId()}">Look</a></td>
						<td><c:out value="${i.getId()}" /></td>
						<td><c:out value="${i.getTitle()}" /></td>
						<td><c:out value="${i.getDescription()}" /></td>
						<td><c:out value="${i.getSpecification()}" /></td>
						<td><c:out value="${i.getDateCreated()}" /></td>
						<td><c:out value="${i.getDateStart()}" /></td>
						<td><c:out value="${i.getDateFinish()}" /></td>
						<td><c:out value="${i.getPrice()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- script
			  src="http://code.jquery.com/jquery-2.2.4.min.js"
			  integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
			  crossorigin="anonymous"></script>
  		 <script src="bootstrap/js/bootstrap.min.js"></script -->

</body>
</html>