<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar">
	   <li class="categories-list"><a href="MainServlet?action=category_list">Categories</a>
	   		<c:choose>
	            <c:when  test="${requestScope.categoryList == 1}">
			   		Equipments
			   		<ul>
			   			<c:forEach items="${list_categories}" var="category">
				   			<c:choose>
			                	<c:when  test="${category.getType() == 'EQ'}">
							     	<li class="category" id="category_${category.getId()}"> <c:out value="${category.getTitle()}" /></li>
						     	</c:when>
					     	</c:choose>
			   			</c:forEach>
			   		</ul>
			   		Accessories
			   		<ul>
			   		    <c:forEach items="${list_categories}" var="category">
		                	<c:choose>
			                	<c:when  test="${category.getType() == 'AC'}">
							     	<li class="category" id="category_${category.getId()}"> <c:out value="${category.getTitle()}" /></li>
						     	</c:when>
					     	</c:choose>
			  			</c:forEach>
			   		</ul>
	   			</c:when>
	   		</c:choose>
	   </li>
	</ul>
</div>