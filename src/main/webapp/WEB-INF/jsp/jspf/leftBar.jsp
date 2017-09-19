<%@ taglib uri="menutag" prefix="menu"%>

<div class="col-sm-3 col-md-2 sidebar">
	<menu:left user="${ user.getUser() }"
				currUrl="${requestScope['javax.servlet.forward.request_uri']}"  containerTag="ul" containerClass="nav nav-sidebar" 
				itemTag="li" currUrlClass="active" 
				currLanguage="${currLanguage}"/>

</div>