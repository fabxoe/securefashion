package domain;
import java.util.ArrayList;
public class ProductService {

    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> products = null;
        ProductDAO pdao = new ProductDAO(); 
        try {
            products = pdao.allproductRetrieve();
        } catch (Exception e) {
            System.out.println("제품 못가져옴"+e);
        }
       // Product product = products.get(0);
       //System.out.println(product.explanation);
        return products;
    }
    
    public ArrayList<Product> getProduct(String productname) {
        ArrayList<Product> products = null;
        ProductDAO pdao = new ProductDAO(); 
        try {
            products = pdao.productRetrieve(productname);
        } catch (Exception e) {
            products = null;
        }
        return products;
    }
    
    public void insertProduct(String producttype, String productname, String explanation, int price, int inventory) {
    	ProductDAO pdao = new ProductDAO(); 
    	pdao.productInsert(producttype, productname, explanation, price, inventory);
    }
    
    public void updateProduct(int productid, String producttype, String productname, String explanation, int price, int inventory) {
    	ProductDAO pdao = new ProductDAO(); 
    	pdao.productUpdate(productid, producttype, productname, explanation, price, inventory);
    }
}
