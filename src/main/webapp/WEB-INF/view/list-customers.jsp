<%@page import="com.madhu.demo.util.SortUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" 
		  href="${pageContext.request.contextPath}/resources/css/style.css">
<meta charset="ISO-8859-1">
<title>Customers List</title>
</head>
<body>
List Customers
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
		
			<!-- put new button: Add Customer -->
			<!-- showFormForAdd will call our Spring Controller Mapping -->
			<input type="button" value="Add Customer"
			onclick="window.location.href='showFormForAdd'; return false;" class="add-button" />
			
			<!--  add a search box -->
			<form:form action="search" method="GET">
				Search Customer: <input type="text" name="theSearchName" />
				
				<input type="submit" value="Search" class="add-button" />
			</form:form>
			
			<div style="clear; both;"></div>
				<p>
					<a href="${pageContext.request.contextPath}/customer/list">Home</a>
				</p>
			</div>
			
			<!--  add our html table here -->
			<table>
				<tr>
					<!-- <th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th> -->
				</tr>
				<!-- setup header links for sorting -->
				<!-- construct a sort link for first name -->
				<c:url var="sortLinkFirstName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.FIRST_NAME) %>" />
				</c:url>
				<c:url var="sortLinkLastName" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.LAST_NAME) %>" />
				</c:url>
				<c:url var="sortLinkEmail" value="/customer/list">
					<c:param name="sort" value="<%= Integer.toString(SortUtils.EMAIL) %>" />
				</c:url>
				
				<tr>
					<th><a href="${sortLinkFirstName}">First Name</a></th>
					<th><a href="${sortLinkLastName}">Last Name</a></th>
					<th><a href="${sortLinkEmail}">Email</a></th>
					<th>Action</th>
				</tr>
				
				<!-- loop over and print our customers -->
				<c:forEach var="tempCustomer" items="${customers}">
					
					<!-- construct an "update" link with customer id -->
					<c:url var="updateLink" value="/customer/showFormForUpdate">
						<c:param name="customerId" value="${tempCustomer.id}" />
					</c:url>
					
					<!-- construct an "delete" link with customer id -->
					<c:url var="deleteLink" value="/customer/delete">
						<c:param name="customerId" value="${tempCustomer.id}" />
					</c:url>
					
					<tr>
						<td> ${tempCustomer.firstName} </td>
						<td> ${tempCustomer.lastName} </td>
						<td> ${tempCustomer.email} </td>
						<!-- Display the update link -->
						<td> <a href="${updateLink}">Update</a>
						|
						<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a> </td>
					</tr>
				</c:forEach>	
			</table>
		</div>
	</div>
</body>
</html>