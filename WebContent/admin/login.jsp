<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="domain.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="domain.Product"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
        <% ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");%>
</head>
        <% User user = (User) request.getAttribute("user");%>
        <% session.setAttribute("user", user);%>
<body>
	     <h2>Hello, <%= user.getUsername()%></h2>
        <form action="paymentlist" method="post">
            <input type="submit" value="All Payment">
        </form>        
        <table border="2px">
            <tr>
                <th width="100">Product ID</th>
                <th width="150">Product Type</th>
                <th width="200">Product Name</th>
                <th width="400">Explanation</th>
                <th width="150">Price</th>
                <th width="100">Inventory</th>
                <th width="100">Update</th>
            </tr>
            <%
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
            %> 
            <tr>
                <td align="center"><%=product.getProductid()%></td>
                <td align="center"><%=product.getProducttype()%></td>
                <td align="center"><%=product.getProductname()%></td>
                <td align="center"><%=product.getExplanation()%></td>
                <td align="center">$<%=product.getPrice()%></td>
                <td align="center"><%=product.getInventory()%></td>
                <td align="center">
                   <form action="update" method="post">
                       <input type="hidden" name="productid" value="<%=product.getProductid()%>">
                       <input type="submit" value="Update">
                   </form>
              	</td>
            </tr>
            <% }%>
        </table>
        <form action="create" method="post">
            <input type="submit" value="Create">
        </form>
</body>
</html>