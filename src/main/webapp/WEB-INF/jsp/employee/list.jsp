<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>
<%@ taglib uri="actiontag" prefix="acl"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="employee.pageTitle.list" />
			</h1>
			<br/>
			<fmt:message key="employee.button.add" var="linkTitle"/>
			<acl:action user="${ user.getUser() }" 
						href="${ ConstantValue.PAGE_EMPLOYEE_ADD_URI }" 
						title="${ linkTitle }" 
						className="btn btn-default" buttonRole="button" 
						id="employee_add_btn" />


			<msg:error errorCode="${ error_code }"
						language="${currLanguage}"
						itemTag="span" containerTag="div"
						bean="employee" containerClass="error_message"/>

			<div class="table-responsive">
			
				<table:employeeList employees="${employee_list}" tableClass="table table-striped tab-content tab-active"
							language="${currLanguage}"  />
				
			</div>

			<ctg:paginator pagingVo="${ paging_vo }" itemTag="li" containerTag="ul" 
				currActionClass="active" containerClass="pagination" appNameAndLang="${appNameAndLang}"/>
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>