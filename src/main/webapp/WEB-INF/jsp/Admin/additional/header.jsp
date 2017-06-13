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
	  	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	      <div class="container-fluid">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="#">DevTeam</a>
	        </div>
	        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav navbar-right">
	            <li class="active"><a href="#">New Orders</a></li>
	            <li><a href="#">Settings</a></li>
	            <li><a href="#">${user.getLogin()}</a></li>
	            <li><a href="#">Help</a></li>
	            <li><a href="Main?action=admin_logout">Exit</a></li>
	          </ul>
	          <form class="navbar-form navbar-right">
	            <input type="text" class="form-control" placeholder="Search...">
	          </form>
	        </div>
	      </div>
    	</div>
    	<c:set var="categoryList" value="0" scope="request" />