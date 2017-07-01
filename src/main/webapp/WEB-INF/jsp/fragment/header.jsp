<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="topmenutag" prefix="tmt"%>

<c:set var="clientLanguage" value="${pageContext.request.locale.language}" scope="request"/>
<c:set var="clientCountry" value="${pageContext.request.locale.country}" scope="request"/>

<c:set var = "curLocale" scope = "request" value = "${clientLanguage}_${clientCountry}"/>

<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="text"/>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><fmt:message key = "admin.sitename" /></title>
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/dashboard.css">
		<link rel="stylesheet" type="text/css" href="styles/style.css">
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
	          <a class="navbar-brand" href="Main"><fmt:message key = "admin.goToMainPage" /></a>
	        </div>
	        <div class="navbar-collapse collapse">
	        
	          <!--ul class="nav navbar-nav navbar-right">
	            <li class="active"><a href="#">New Orders</a></li>
	            <li><a href="#">Settings</a></li>
	            <li><a href="#">${user.getUser().getLogin()}</a></li>
	            <li><a href="#">Help</a></li>
	            <li><a href="Main?action=logout">Exit</a></li>
	          </ul-->
			  <tmt:topmenu user="${ user.getUser() }"
				currAction="${param.action}"  containerTag="ul" containerClass="nav navbar-nav navbar-right" itemTag="li" currActionClass="active" language="${clientLanguage}" country="${clientCountry}"/>
	          <!-- form class="navbar-form navbar-right">
	            <input type="text" class="form-control" placeholder="Search...">
	          </form -->
	        </div>
	      </div>
    	</div>