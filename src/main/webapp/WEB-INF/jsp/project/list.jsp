<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="errormessagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="project.pageTitle.list" />
			</h1>
			<msg:error errorCode="${ error_code }"  itemTag="span" containerTag="div"
						language="${clientLanguage}" country="${clientCountry}"
						bean="project" containerClass="error_message"/>

			<%@include file="jspf/list.jsp"%>
			
			<ctg:paginator uri="${ uri }" itemTag="li" containerTag="ul" currActionClass="active" containerClass="pagination"
				currPage="${ currPage }"
				countPages="${ countPages }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>