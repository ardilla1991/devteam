<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a onclick="getContent('categoryContent',  'MainServlet?action=equipment_list_by_category&category_id=${category_id}')" href="#">Go back</a>

<form id="equipment_add" name="eq_form" action="javascript:void(null);" 
		method="post" enctype="multipart/form-data" 
		onsubmit="elementAdd('equipment_add', 'MainServlet?action=${equipment.getId() ? 'edit' : 'add'}_equipment&category_id=${category_id}', 'categoryContent');">
	<table class="table table-striped">
              <thead>
                <tr>
                  <th>Param title</th>
                  <th>Param value</th>
                </tr>
              </thead>
              <tbody>
              		<tr>
              			<td>Model</td>
              			<td><input type="text" name="model" value="${equipment.getModel()}" /></td>	
              		</tr>
              		<tr>
              			<td>Price</td>
              			<td><input type="text" name="price" value="${equipment.getPrice()}" /></td>	
              		</tr>
              		<tr>
              			<td>Weight</td>
              			<td><input type="text" name="weight" value="${equipment.getWeight()}" /></td>	
              		</tr>
              		<tr>
              			<td>Width</td>
              			<td><input type="text" name="width" value="${equipment.getWidth()}" /></td>	
              		</tr>
              		<tr>
              			<td>height</td>
              			<td><input type="text" name="height" value="${equipment.getHeight()}" /></td>	
              		</tr>
              		<tr>
              			<td>Person category</td>
              			<td>
              				<select name="person_category">
              					<c:forEach items="${person_categories}" var="category">
              						<option value="${category}" 
              						${PersonCategoryEnum.category.toString()==PersonCategoryEnum.equipment.getPersonCategory().toString() 
              						? 'selected="selected"' 
              						: ''}><c:out value="${category}" /></option>
              					</c:forEach>
              				</select>
						</td>	
              		</tr>
              		              		
	                <c:forEach items="${equipment.getParameters()}" var="parameter">
						<tr> 
						     <td> <c:out value="${parameter.getTitle()}"/> </td>
						     <td>
						     	<input type="text" name="param_${parameter.getId()}" value="${parameter.getValue()}" />
						     	<div><c:out value="${parameter.getDescription()}"/></div>
						     	
						     </td>
						</tr>
			  		</c:forEach>
              </tbody>
            </table>
            <input type="hidden" name="id" value="${equipment.getId()}" />
      <input type="submit" name="button" value="add"/>
</form>