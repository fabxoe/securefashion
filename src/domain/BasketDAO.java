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

public class BasketDAO {

	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;
	
	public BasketDAO() {
    	Context init;
    	try {
			init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			System.out.println("BasketDAO-Connection pool 이용한 DB 연결의 성공");
		} catch (NamingException e) {
			System.out.println("BasketDAO-Connection pool 이용한 DB 연결의 실패: " + e);
			return;
		}
    }
	
    private static final String RETRIEVE_STMT
            = "SELECT * FROM shoppingbasket where UserID = ? and Validity = 1";
    private static final String GETID_STMT = "SELECT COUNT(BasketID) FROM shoppingbasket";
    private static final String ADD_STMT = "INSERT INTO shoppingbasket VALUES(?,?,?,?,1)";
    private static final String DELETE_STMT = "UPDATE shoppingbasket SET Validity = 2 WHERE UserID = ? AND BasketID = ?";
    private static final String CLEAN_STMT = "UPDATE shoppingbasket SET Validity = 2 WHERE UserID = ?";
    
    ArrayList<Basket> basketRetrieve(int userid) throws SQLException {
    	System.out.println("basketRetrieve()진입");

    	ArrayList<Basket> baskets = new ArrayList<Basket>();    
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(RETRIEVE_STMT);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int BasketID = rs.getInt(1);
                int UserID = rs.getInt(2);
                int ProductID = rs.getInt(3);
                int Numbers = rs.getInt(4);
                int Validity = rs.getInt(5);
                baskets.add(new Basket(BasketID, UserID, ProductID, Numbers, Validity));
            }
            return baskets;
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
    void basketAdd(int userid, int productid, int numbers) {
    	System.out.println("basketAdd()진입");
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(GETID_STMT);
            rs = pstmt.executeQuery();
            int ID = -1;
            rs.next();
            ID = rs.getInt("COUNT(BASKETID)");
            System.out.println("ID값 :"+ID);
            ID++;
            pstmt = con.prepareStatement(ADD_STMT);
            pstmt.setInt(1, ID);
            pstmt.setInt(2, userid);
            pstmt.setInt(3, productid);
            pstmt.setInt(4, numbers);
            pstmt.executeQuery();
        } catch (SQLException se) {
            throw new RuntimeException(
                    "A database error occurred. " + se.getMessage());
        } finally {
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
    void basketDelete(int userid, int basketid) {
    	System.out.println("basketDelete()진입");
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(DELETE_STMT);
            pstmt.setInt(1, userid);
            pstmt.setInt(2, basketid);
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
    void basketClean(int userid) {
    	System.out.println("basketClean()진입");
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(CLEAN_STMT);
            pstmt.setInt(1, userid);
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