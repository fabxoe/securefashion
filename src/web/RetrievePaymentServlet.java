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

import domain.Payment;
import domain.PaymentService;
import domain.User;

@WebServlet("/paymentlist")
public class RetrievePaymentServlet extends HttpServlet{
	public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
    	System.out.println("RetrievePaymentServlet()진입");
        RequestDispatcher view = null;
        HttpSession session = request.getSession();
        int userid = ((User) session.getAttribute("user")).getUserid();
        System.out.println("RetrievePaymentServlet 내에서 Userid 받아옴");
        
        String usertype = ((User) session.getAttribute("user")).getUsertype();
        System.out.println("RetrievePaymentServlet 내에서 Usertype 받아옴");
        
        ArrayList<Payment> payments = null;
        
        PaymentService PaymentService = new PaymentService();
        
        if(usertype.equals("C")) {
        	payments = PaymentService.getAllPayment(userid);
        }else {
        	payments = PaymentService.getAllPayment();
        }
        
        System.out.println("RetrievePaymentServlet 내에서 payments 받아옴");
        //System.out.println("RetrievePaymentServlet 내에서 pay정보출력: "+payments.get(0).getPaymentid());
        request.setAttribute("user", session.getAttribute("user"));
        request.setAttribute("payments", payments);
        
        if(usertype.equals("C")) {
        	view = request.getRequestDispatcher("payment.jsp");
        	view.forward(request, response);        	
        }else {
        	view = request.getRequestDispatcher("admin/payment.jsp");
        	view.forward(request, response);        	
        }
    }
} 
