<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="messagetag" prefix="msg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="clientLanguage" value="${pageContext.request.locale.language}" scope="request"/>
<c:set var="clientCountry" value="${pageContext.request.locale.country}" scope="request"/>

<c:set var = "curLocale" scope = "request" value = "${clientLanguage}_${clientCountry}"/>

<fmt:setLocale value="${curLocale}"/>
<fmt:setBundle basename="text"/>

<div class="error_message">
	<c:if test="${ error_code > 0}">
		<msg:message errorCode="${ error_code }" msgList="${ empty_field }"
			language="${clientLanguage}" country="${clientCountry}"  itemTag="span" containerTag="div"
			bean="project" />
	</c:if>
</div>

<c:forEach items="${project_list}" var="i">
	<a class="project_title"  href="Main?action=project_view&project_id=${i.getId()}" title="<fmt:message key="project.action.view" />"><c:out value="${i.getTitle()}" /></a>
</c:forEach>