<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="menutag" prefix="menu"%>

<fmt:setLocale value="${currLanguage}"/>
<fmt:setBundle basename="text"/>

<html>
	<head>
		<meta http-equiv="Content-Type">
		<c:choose>
			<c:when test="${ empty page_title}">
				<c:set var="p_title" scope="page"><fmt:message key = "admin.sitename" /></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="p_title" scope="page">${page_title}</c:set>
			</c:otherwise>
		</c:choose>
		<title>${p_title }</title>
		<link href="${ appPath }bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ appPath }style/dashboard.css">
		<link rel="stylesheet" type="text/css" href="${ appPath }style/style.css">
	</head>
	<body>
		<%@ page import="by.htp.devteam.controller.util.ConstantValue" %>
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
			  <menu:top user="${ user.getUser() }"
				currUrl="${requestScope['javax.servlet.forward.request_uri']}"  containerTag="ul" 
				containerClass="nav navbar-nav navbar-right" itemTag="li" 
				currUrlClass="active" currLanguage="${currLanguage}"/>
	          <%@ page import="by.htp.devteam.bean.UserRole" %>
			  <c:if test="${ user.getUser().getRole()  == RoleEnum.DEVELOPER || user.getUser().getRole()  == RoleEnum.MANAGER }">
		          ssh<form class="navbar-form navbar-right" action="${appNameAndLang}/${ ConstantValue.PAGE_PROJECT_FOUND_URI }" method="GET">
		          		<input type="hidden" name="action" value="project_find">
		          		<input type="text" class="form-control" autocomplete="off" placeholder="<fmt:message key = "project.goTo" />" id="projectName" name="title" maxlength="250"/>
		          	
		          		<div style="display: none;" id="projectList"></div>
		          </form>
	          </c:if>
	        </div>
	      </div>
    	</div>