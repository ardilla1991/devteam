<%@ taglib uri="messagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>

<div class="error_message">
	<c:if test="${ error_code > 0}">
		<msg:message errorCode="${ error_code }" language="${clientLanguage}"
			country="${clientCountry}" itemTag="span" containerTag="div"
			bean="employee" />
	</c:if>
</div>
<div class="table-responsive">

	<table:employeeList employees="${employee_list}"
		tableClass="table table-striped tab-content tab-active"
		language="${clientLanguage}" country="${clientCountry}" user="${ user.getUser() }" />

</div>
