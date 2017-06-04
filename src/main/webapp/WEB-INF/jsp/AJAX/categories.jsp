<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a onclick="getContent('categoryContent',  'MainServlet?action=viewAddEquipmentPage&category_id=${category_id}')" href="#">Add equipment</a>

<table class="table table-striped tab-content tab-active">
              <thead>
                <tr>
                  <th>Action</th>
                  <th>#</th>
                  <th>Model</th>
                  <th>Price</th>
                  <th>Weight</th>
                  <th>Width</th>
                  <th>Height</th>
                  <th>PersonCategory</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${list_eq}" var="equip">
					<tr> 
					     <td> <a onclick="getContent('categoryContent',  'MainServlet?action=viewEditEquipmentPage&id=${equip.getId()}&category_id=${category_id}')" href="#">Edit</a></td>
					     <td> <c:out value="${equip.getId()}"/> </td>
					     <td> <c:out value="${equip.getModel()}" /> </td>
					     <td id="price_${equip.getId()}"> <c:out value="${equip.getPrice()}" /> </td>
					     <td> <c:out value="${equip.getWeight()}" /> </td>
					     <td> <c:out value="${equip.getWidth()}" /> </td>
					     <td> <c:out value="${equip.getHeight()}" /> </td>
					     <td> <c:out value="${equip.getPersonCategory()}" /> </td>
					</tr>
		  		</c:forEach>
              </tbody>
            </table>