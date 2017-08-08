<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<%@include file="../employee/jspf/list.jsp"%>
			<h1 class="page-header">
				<fmt:message key="user.pageTitle.list" />
			</h1>
			<msg:error errorCode="${ error_code }"
						language="${clientLanguage}" country="${clientCountry}" itemTag="span" containerTag="div"
						bean="user" containerClass="error_message"/>
			<div class="table-responsive">
			
				<table:userList users="${ user_list }" tableClass="table table-striped tab-content tab-active"
							language="${clientLanguage}" country="${clientCountry}" />
				
			</div>

			<ctg:paginator pagingVo="${ paging_vo }" itemTag="li" containerTag="ul" 
				currActionClass="active" containerClass="pagination" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>