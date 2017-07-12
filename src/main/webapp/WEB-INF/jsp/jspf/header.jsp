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
	          <a class="navbar-brand" href="#"><fmt:message key = "admin.goToMainPage" /></a>
	        </div>
	        <div class="navbar-collapse collapse">
			  <tmt:topmenu user="${ user.getUser() }"
				currAction="${param.action}"  containerTag="ul" containerClass="nav navbar-nav navbar-right" itemTag="li" currActionClass="active" language="${clientLanguage}" country="${clientCountry}"/>
	          <%@ page import="by.htp.devteam.bean.UserRole" %>
			  <c:if test="${ user.getUser().getRole()  == RoleEnum.DEVELOPER || user.getUser().getRole()  == RoleEnum.MANAGER }">
		          <form class="navbar-form navbar-right" action="Main" method="GET">
		          		<input type="hidden" name="action" value="project_find">
		          		<input type="text" class="form-control" autocomplete="off" placeholder="<fmt:message key = "project.goTo" />" id="projectName" name="title" maxlength="250"/>
		          	
		          		<div style="display: none;" id="projectList"></div>
		          </form>
	          </c:if>
	        </div>
	      </div>
    	</div>