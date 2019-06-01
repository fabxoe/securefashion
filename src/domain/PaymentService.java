package domain;

import java.util.ArrayList;
public class PaymentService {
    
    private PaymentDAO paymentDataAccess;
    
    public PaymentService() {
        paymentDataAccess = new PaymentDAO();
    }
    
    public ArrayList<Payment> getAllPayment() {
        ArrayList<Payment> payments = null;
        try {
            payments = paymentDataAccess.allpaymentRetrieve();
        } catch (Exception e) {
            payments = null;
        }
        return payments;
    }
    
    public ArrayList<Payment> getAllPayment(int userid) {
    	System.out.println("getAllPayment진입");
        ArrayList<Payment> payments = null;
        try {
            payments = paymentDataAccess.paymentRetrieve(userid);
            System.out.println("getAllPayment에서 payments가져옴");
            //Payment pay1 = payments.get(0);
            //System.out.println(pay1.productid);
        } catch (Exception e) {
            payments = null;
        }
        return payments;
    }
    
    public void paymentAdd(int userid, int productid, int numbers, String address, String contact, String creditcardnumber, String creditcardpassword) {
        paymentDataAccess.paymentAdd(userid, productid, numbers, address, contact, creditcardnumber, creditcardpassword);
    }
    
}