<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin page</title>
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/dashboard.css">
		<link rel="stylesheet" type="text/css" href="styles/admin_styles.css">
	</head>
	<body>  	
	  <%@include file="additional/header.jsp"%>
	  <c:set var="categoryList" value="1" scope="request" />

    <div class="container-fluid">
      <div class="row">
        
        <%@include file="additional/leftCol.jsp"%>
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">Category page</h1>

		 

          <!-- h2 class="sub-header">Rented Equipment</h2 -->
          <div class="table-responsive" id="categoryContent">
			 Choose category
          </div>
        </div>
      </div>
    </div>
	  	
	<%@include file="additional/footer.jsp"%>	  	
	  	
	</body>
</html>