<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		
	    <div class="container">
	    	<c:if test ="${not empty  error_message}">${error_message}</c:if>
	    	<form class="form-signin" role="form" action="Main" method="POST">
		        <h2 class="form-signin-heading">Please sign in</h2>
		        <input type="text" class="form-control" placeholder="Login" required autofocus  name="login" />
		        <input type="password" class="form-control" placeholder="Password" required name="pass" />
		        <label class="checkbox">
		          <input type="checkbox" value="remember-me" /> Remember me
		        </label>
		        <input type="hidden" name="action" value="admin_login" />
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		    </form>
    	</div> <!-- /container -->
	
		<!-- script
			  src="http://code.jquery.com/jquery-2.2.4.min.js"
			  integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
			  crossorigin="anonymous"></script>
  		 <script src="bootstrap/js/bootstrap.min.js"></script -->
		
	</body>
</html>