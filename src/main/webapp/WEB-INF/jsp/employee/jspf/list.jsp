<%@ taglib uri="errormessagetag" prefix="msg"%>
<%@ taglib uri="listAsTable" prefix="table"%>

<msg:error errorCode="${ error_code }" language="${currLanguage}"
			 itemTag="span" containerTag="div"
			bean="employee" containerClass="error_message"/>


<div class="table-responsive">

	<table:employeeList employees="${employee_list}"
		tableClass="table table-striped tab-content tab-active"
		language="${currLanguage}" user="${ user.getUser() }" />

</div>
