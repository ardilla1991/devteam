<%@include file="../jspf/header.jsp"%>

<div class="container-fluid">
	<div class="row">
		<%@include file="../jspf/leftBar.jsp"%>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="col-sm-5">
				<h1 class="page-header">
					<fmt:message key="order.pageTitle.view" />
				</h1>
				<%@include file="jspf/view.jsp"%>
			</div>
			<div class="col-sm-7">
				<%@ page import="by.htp.devteam.bean.UserRole" %>
				<c:if test="${ user.getUser().getRole()  !=  UserRole.CUSTOMER && order_vo.getOrder().getId() > 0}">
					<a class="btn btn-default" role="button"
						href="Main?action=project_show_add_form&order_id=${order_vo.getOrder().getId()}"
						id="project_add"><fmt:message key="project.button.add" /></a>
					<div id="project_edit"></div>
				</c:if>
			</div>
		</div>
	</div>
</div>

</body>
</html>
<%@include file="../jspf/footer.jsp"%>
