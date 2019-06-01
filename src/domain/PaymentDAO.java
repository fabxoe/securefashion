package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PaymentDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;
	
	public PaymentDAO(){
		Context init;
		
		try {
			init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			System.out.println("PaymentDAO-Connection pool 이용한 DB 연결의 성공");
		} catch (NamingException e) {
			System.out.println("PaymentDAO-Connection pool 이용한 DB 연결의 실패: " + e);
			return;
		}
	}
    private static final String ALLRETRIEVE_STMT
            = "SELECT * FROM shoppingpayment";
    private static final String RETRIEVE_STMT
            = "SELECT * FROM shoppingpayment WHERE UserID = ?";
    private static final String GETID_STMT = "SELECT COUNT(PaymentID) FROM shoppingpayment";
    private static final String ADD_STMT = "INSERT INTO shoppingpayment VALUES(?,?,?,?,?,?,?,?)";
    
    ArrayList<Payment> allpaymentRetrieve() throws SQLException {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(ALLRETRIEVE_STMT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int PaymentID = rs.getInt(1);
                int UserID = rs.getInt(2);
                int ProductID = rs.getInt(3);
                int Numbers = rs.getInt(4);
                String Address = rs.getString(5);
                String Contact = rs.getString(6);
                String CreditCardNumber = rs.getString(7);
                String CreditCardPassword = rs.getString(8);
                payments.add(new Payment(PaymentID, UserID, ProductID, Numbers, Address, Contact, CreditCardNumber, CreditCardPassword));
            }
            return payments;
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    ArrayList<Payment> paymentRetrieve(int userid) throws SQLException {
    	System.out.println("paymentRetrieve()진입");
        ArrayList<Payment> payments = new ArrayList<Payment>();
    
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(RETRIEVE_STMT);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int PaymentID = rs.getInt(1);
                int UserID = rs.getInt(2);
                int ProductID = rs.getInt(3);
                int Numbers = rs.getInt(4);
                String Address = rs.getString(5);
                String Contact = rs.getString(6);
                String CreditCardNumber = rs.getString(7);
                String CreditCardPassword = rs.getString(8);
                payments.add(new Payment(PaymentID, UserID, ProductID, Numbers, Address, Contact, CreditCardNumber, CreditCardPassword));
            }
            return payments;
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
    
    void paymentAdd(int userid, int productid, int numbers, String address, String contact, String creditcardnumber, String creditcardpassword) {
        
    	try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GETID_STMT);
            rs = pstmt.executeQuery();
            int ID = -1;
            rs.next();
            ID = rs.getInt("COUNT(PaymentID)");
            ID++;
            pstmt = con.prepareStatement(ADD_STMT);
            pstmt.setInt(1, ID);
            pstmt.setInt(2, userid);
            pstmt.setInt(3, productid);
            pstmt.setInt(4, numbers);
            pstmt.setString(5, address);
            pstmt.setString(6, contact);
            pstmt.setString(7, creditcardnumber);
            pstmt.setString(8, creditcardpassword);
            pstmt.executeQuery();
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}