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

public class ProductDAO {
	
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;
	
  
    public ProductDAO(){
    	Context init;
    	try {
			init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			System.out.println("ProductDAO-Connection pool 이용한 DB 연결의 성공");
		} catch (NamingException e) {
			System.out.println("ProductDAO-Connection pool 이용한 DB 연결의 실패: " + e);
			return;
		}
    }
    
    private static final String ALLRETRIEVE_STMT = "SELECT * FROM shoppingproduct";
    private static final String INSERT_STMT = "INSERT INTO shoppingproduct VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_STMT = "UPDATE shoppingproduct SET ProductType = ? ProductName = ? Explanation = ? Price = ? Inventory = ? WHERE ProductID = ?";
    private static final String GETID_STMT = "SELECT COUNT(ProductID) FROM shoppingproduct";
    
    ArrayList<Product> allproductRetrieve() throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(ALLRETRIEVE_STMT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	//System.out.println("값있음");
                int ProductID = rs.getInt(1);
                String ProductType = rs.getString(2);
                String ProductName = rs.getString(3);
                String Explanation = rs.getString(4);
                int Price = rs.getInt(5);
                int Inventory = rs.getInt(6);
                products.add(new Product(ProductID, ProductType, ProductName, Explanation, Price, Inventory));
            }
            
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
        return products;
    }
    ArrayList<Product> productRetrieve(String productname) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM shoppingproduct WHERE ProductName like '%" + productname + "%'");
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int ProductID = rs.getInt(1);
                String ProductType = rs.getString(2);
                String ProductName = rs.getString(3);
                String Explanation = rs.getString(4);
                int Price = rs.getInt(5);
                int Inventory = rs.getInt(6);
                products.add(new Product(ProductID, ProductType, ProductName, Explanation, Price, Inventory));
            }
            return products;
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
    void productInsert(String producttype, String productname, String explanation, int price, int inventory) {

        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GETID_STMT);
            rs = pstmt.executeQuery();
            int ID = -1;
            rs.next();
            ID = rs.getInt("COUNT(ProductID)");
            ID++;
            pstmt = con.prepareStatement(INSERT_STMT);
            pstmt.setInt(1, ID);
            pstmt.setString(2, producttype);
            pstmt.setString(3, productname);
            pstmt.setString(4, explanation);
            pstmt.setInt(5, price);
            pstmt.setInt(6, inventory);
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
    void productUpdate(int productid, String producttype, String productname, String explanation, int price, int inventory) {
     
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE_STMT);
            pstmt.setString(1, producttype);
            pstmt.setString(2, productname);
            pstmt.setString(3, explanation);
            pstmt.setInt(4, price);
            pstmt.setInt(5, inventory);
            pstmt.setInt(6, productid);
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