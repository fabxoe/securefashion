package domain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class UserDAO {
	
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	public UserDAO() {
		Context init;
		try {
			init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			System.out.println("UserDAO-Connection pool 이용한 DB 연결의 성공");
		} catch (NamingException ex) {
			System.out.println("UserDAO-Connection pool 이용한 DB 연결의 실패: " + ex);
			return;
		}
	}
	
   
    private static final String RETRIEVE_STMT
            = "SELECT * FROM shoppinguser WHERE UserType=? AND UserName=? AND Password =?";
    private static final String GETID_STMT = "SELECT COUNT(UserID) FROM shoppinguser";
    private static final String CREATE_STMT = "INSERT INTO shoppinguser VALUES(?,?,?,?,?,?,?,?,?)";

    User userRetrieve(String usertype, String username, String password) throws SQLException {
    	User user=null;
        int rows = 0;
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(RETRIEVE_STMT);
            pstmt.setString(1, usertype);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int UserID = rs.getInt("UserID");
                String UserType = rs.getString("UserType");
                String UserName = rs.getString("UserName");
                String Password = rs.getString("Password");
                Date BirthDate = rs.getDate("BirthDate");
                String Gender = rs.getString("Gender");
                String Email = rs.getString("Email");
                String Contact = rs.getString("Contact");
                String Address = rs.getString("Address");
                rows++;
                if (rows > 1) {
                    throw new SQLException("Too many rows were returned.");
                }
                user = new User(UserID, UserType, UserName, Password, BirthDate, Gender, Email, Contact, Address);
            }
            return user;
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

    void userCreate(String usertype, String username, String password, Date birthdate, String gender, String email, String contact, String address) {
       
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GETID_STMT);
            rs = pstmt.executeQuery();
            int ID = -1;
            rs.next();
            ID = rs.getInt("COUNT(UserID)");
            ID++;
            pstmt = con.prepareStatement(CREATE_STMT);
            pstmt.setInt(1, ID);
            pstmt.setString(2, usertype);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setDate(5, birthdate);
            pstmt.setString(6, gender);
            pstmt.setString(7, email);
            pstmt.setString(8, contact);
            pstmt.setString(9, address);
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