<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<%@include file="../employee/jspf/list.jsp"%>
			<h1 class="page-header">
				<fmt:message key="user.pageTitle.list" />
			</h1>
			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }"
						language="${clientLanguage}" country="${clientCountry}" itemTag="span" containerTag="div"
						bean="user" />
				</c:if>
			</div>
			<div class="table-responsive">
			
				<table:userList users="${user_list_vo.getUsers()}" tableClass="table table-striped tab-content tab-active"
							language="${clientLanguage}" country="${clientCountry}" />
				
			</div>

			<ctg:paginator uri="${ uri }" itemTag="li" containerTag="ul" currActionClass="active" containerClass="pagination"
				currPage="${ user_list_vo.getCurrPage() }"
				countPages="${ user_list_vo.getCountPages() }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>