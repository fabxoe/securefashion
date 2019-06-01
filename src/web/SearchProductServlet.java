package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Product;
import domain.ProductService;

@WebServlet("/search")
public final class SearchProductServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher view = null;
		ProductService ProductService = null;
		String ProductName = request.getParameter("productname");
		HttpSession session = request.getSession();
		
		ArrayList<Product> products = null;
		ProductService = new ProductService();
		products = ProductService.getProduct(ProductName);
		request.setAttribute("products", products);
		request.setAttribute("user", session.getAttribute("user"));
		
		view = request.getRequestDispatcher("login.jsp");
		view.forward(request, response);
	}
}