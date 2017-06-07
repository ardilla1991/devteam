<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="pagetags" prefix="ctg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin page</title>
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/dashboard.css">
	</head>
	<body> 
	 	
	<%@include file="additional/header.jsp"%>

	    <div class="container-fluid">
	      <div class="row">
	      
	        <%@include file="additional/leftCol.jsp"%>
	        
	        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	          <h1 class="page-header">New Projects</h1>
	
	          <!-- h2 class="sub-header">Rented Equipment</h2 -->
	          <div class="table-responsive">
	            <table class="table table-striped tab-content tab-active">
	              <thead>
	                <tr>
	                  <th>Actions</th>
	                  <th>#</th>
	                  <th>Title</th>
	                  <th>Description</th>
	                  <th>Date Created</th>
	                  <th>Project start date</th>
	                  <th>Project finish date</th>
	                  <th>Customer information</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach items="${project_list}" var="i">
				     	<tr> 
				     		<td> <a href="Admin?action=project_edit&project_id=${i.getId()}">Edit</a></td>
				     		<td> <c:out value="${i.getId()}"/> </td>
				    		<td> <c:out value="${i.getTitle()}" /> </td>
				    		<td> <c:out value="${i.getDescription()}" /> </td>
				    		<td> <c:out value="${i.getDateCreated()}" /> </td>
				    		<td> <c:out value="${i.getDateStart()}" /> </td>
				    		<td> <c:out value="${i.getDateFinish()}" /> </td>
				    		<td> <c:out value="${i.getCustomer().getName()}" />, 
				    			 <c:out value="${i.getCustomer().getEmail()}" />,
				    			 <c:out value="${i.getCustomer().getPhone()}" />
				    		</td>
				     	</tr>
			  		</c:forEach>
	              </tbody>
	            </table>
	          </div>

	          <!-- ctg:paginator countPages="${ user }"/ -->
	          <ctg:paginator  uri="Admin?action=projects_new_list" currPage="${ currPage }" countPages="${ countPages }"/>
	          
	        </div>
	      </div>
	    </div>
	  	
	   	<%@include file="additional/footer.jsp"%>
	  	
	</body>
</html>