<%@include file="../jspf/header.jsp"%>
<%@ taglib uri="pagetag" prefix="ctg"%>
<%@ taglib uri="messagetag" prefix="msg"%>

<div class="container-fluid">
	<div class="row">

		<%@include file="../jspf/leftBar.jsp"%>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<h1 class="page-header">
				<fmt:message key="project.pageTitle.list" />
			</h1>
			<div class="error_message">
				<c:if test="${ error_code > 0}">
					<msg:message errorCode="${ error_code }"
						language="${clientLanguage}" country="${clientCountry}"
						bean="project" />
				</c:if>
			</div>
			<c:set var="project_list" value="${project_list_vo.getProjects()}" scope="request" />
			<%@include file="jspf/list.jsp"%>
			
			<ctg:paginator uri="${ uri }"
				currPage="${ project_list_vo.getCurrPage() }"
				countPages="${ project_list_vo.getCountPages() }" />
		</div>
	</div>
</div>


<%@include file="../jspf/footer.jsp"%>