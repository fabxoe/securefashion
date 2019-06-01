<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
​​<%@page import="domain.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="domain.Product"%>
<!-- 이 프로젝트의 login.jsp페이지는 사용자가 main.jsp에서 로그인 후에 펼쳐지는 진정한 메인페이지. -->
<!-- 실제 쇼핑몰에는 처음부터 일반적으로 로그인 기능만으로 한 화면을 완전히 가리는 main.jsp가 존재하지 않는다. -->
<!-- 오히려 상품이 보이는 진정한 메인페이지의 구석에 있는 로그인하기 버튼을 클릭하면 main.jsp와 같은 기능의 페이지를 보여줘야한다.-->
<!-- 그 예시. https://themewagon.com/themes/free-html5-ecommerce-website-template/ -->

<!-- login.jsp페이지는 로그인을 처리하는 페이지가 아니라 로그인을 처리하는 서블릿이 결과를 -->
<!-- 처리하고나서 전달하는 값을 보여주기만 할 뿐. -->
<!--  파일명 때문에 헷갈릴까봐, 다시 한번 말하지만, 로그인 기능이 아니라 로그인을 하고난 결과를 보여준다!  -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
	<% ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products"); %>
	<% User user = (User)request.getAttribute("user"); %>
	<% session.setAttribute("user", user); %>
</head>
<body>
	<h2>Hello, <%= user.getUsername() %></h2>
	<form action="basket" method="post">
		<input type="hidden" name="userid" value="<%=user.getUserid()%>">
		<input type="submit" value="My Basket">
	</form>		
	<form action="paymentlist" method="post">
        <input type="submit" value="My Payment">
    </form>
	<form action="search" method="post">
	    Search the product you want :
	    <input type="text" name="productname" size="24">
	    <input type="submit" value="Submit">
	</form>
	
	<table border="2px">
	    <tr>
	        <th width="100">Product ID</th>
	        <th width="150">Product Type</th>
	        <th width="200">Product Name</th>
	        <th width="400">Explanation</th>
	        <th width="150">Price</th>
	        <th width="100">Inventory</th>
	        <th width="200">Take in Basket</th>
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
	        	<form action="take" method="post">
	        		Enter the numbers you want:
	        		<input type="hidden" name="userid" value="<%= user.getUserid() %>">
	        		<input type="hidden" name="productid" value="<%=product.getProductid()%>">
                    <input type="text" name="numbers" size="5">
                    <input type="submit" value="Take">
	        	</form>
	        </td>
	    </tr>
	    <% } %>
	  </table>
</body>
</html>