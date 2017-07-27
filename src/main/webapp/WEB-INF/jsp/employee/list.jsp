<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="employee.pageTitle.list" />
			</h1>
			
			<%@ page import="by.htp.devteam.bean.UserRole" %>
			<br/>
			<c:if test="${ user.getUser().getRole()  ==  UserRole.MANAGER}">
				<a class="btn btn-default" role="button" id="employee_add_btn"
						href="Main?action=employee_show_add_form"
						><fmt:message key="employee.button.add" /></a>
			</c:if>

			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }"
						language="${clientLanguage}" country="${clientCountry}" itemTag="span" containerTag="div"
						bean="employee" />
				</c:if>
			</div>
			<div class="table-responsive">
			
				<table:employeeList employees="${employee_list.getEmployees()}" tableClass="table table-striped tab-content tab-active"
							language="${clientLanguage}" country="${clientCountry}" />
				
			</div>

			<ctg:paginator uri="${ uri }" itemTag="li" containerTag="ul" currActionClass="active" containerClass="pagination"
				currPage="${ employee_list.getCurrPage() }"
				countPages="${ employee_list.getCountPages() }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>