<%@ taglib uri="leftmenutag" prefix="lmt"%>

<div class="col-sm-3 col-md-2 sidebar">
	<lmt:leftmenu user="${ user.getUser() }"
				currAction="${param.action}"  containerTag="ul" containerClass="nav nav-sidebar" itemTag="li" currActionClass="active" 
				language="${clientLanguage}" country="${clientCountry}"/>

</div>