
package badrbillingsystem.repos.productmovement;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.returndocumentheader.ReturnDocumentHeaderRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductMovementRepo implements IProductMovementRepo{

    @Override
    public boolean save(ProductMovement productMovement) {
        String sql = "INSERT INTO TB_PRODUCT_MOVEMENT (PRODUCT_ID,SALES_INVOICE_ID,RETURN_INVOICE_ID,SALES_QUANTITY,RETURN_QUANTITY,MOVEMENT_INFO,DATE,DETAILS,CUSTOMER_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, productMovement.getProductId());
            ps.setLong(2, productMovement.getSalesInvoiceId());
            ps.setLong(3, productMovement.getReturnInvoiceId());
            ps.setDouble(4, productMovement.getSalesQuantity());
            ps.setDouble(5, productMovement.getReturnQuantity());
            if(productMovement.getSalesInvoiceId() > 0) {
                SalesInvoiceHeaderRepo repo = new SalesInvoiceHeaderRepo();
                SalesInvoiceHeader header = repo.findById(productMovement.getSalesInvoiceId());
                ps.setString(6, "فاتورة مبيعات رقم " + productMovement.getSalesInvoiceId());
                ps.setString(7, header.getDate());
            }
            else if(productMovement.getReturnInvoiceId() > 0){
                ReturnDocumentHeaderRepo repo = new ReturnDocumentHeaderRepo();
                ReturnDocumentHeader header = repo.findById(productMovement.getReturnInvoiceId());              
                ps.setString(6, "مردود مبيعات رقم " + productMovement.getReturnInvoiceId());
                ps.setString(7, header.getDate());
            }
            
            ps.setString(8, productMovement.getDetails());
            ps.setLong(9, productMovement.getCustomerId());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean updateBySalesInvoiceId(ProductMovement newProductMovement, long invoiceId) {
        String sql = "UPDATE TB_PRODUCT_MOVEMENT SET SALES_QUANTITY=?,DETAILS=? WHERE PRODUCT_ID=? AND SALES_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);            
            ps.setDouble(1, newProductMovement.getSalesQuantity());
            ps.setString(2, newProductMovement.getDetails());
            ps.setLong(3, newProductMovement.getProductId());
            ps.setLong(4, newProductMovement.getSalesInvoiceId());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }
    
    @Override
    public boolean updateByReturnInvoiceId(ProductMovement newProductMovement, long invoiceId) {
        String sql = "UPDATE TB_PRODUCT_MOVEMENT SET RETURN_QUANTITY=?,DETAILS=? WHERE PRODUCT_ID=? AND RETURN_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);  
            ps.setDouble(1, newProductMovement.getReturnQuantity());
            ps.setString(2, newProductMovement.getDetails());
            ps.setLong(3, newProductMovement.getProductId());
            ps.setLong(4, newProductMovement.getReturnInvoiceId());    
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean deleteBySalesInvoiceId(long productId, long invoiceId) {
        String sql = "DELETE FROM TB_PRODUCT_MOVEMENT WHERE PRODUCT_ID=? AND SALES_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, productId);
            ps.setLong(2, invoiceId);
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }
    
    @Override
    public boolean deleteByReturnInvoiceId(long productId, long invoiceId) {
        String sql = "DELETE FROM TB_PRODUCT_MOVEMENT WHERE PRODUCT_ID=? AND RETURN_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, productId);
            ps.setLong(2, invoiceId);
            System.out.println("============================");
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public ArrayList<ProductMovement> findAllById(long id) {
        String sql = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE PRODUCT_ID=?";
        ArrayList<ProductMovement> list = new ArrayList();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductMovement p = new ProductMovement();
                p.setProductId(rs.getLong("PRODUCT_ID"));
                p.setReturnInvoiceId(rs.getLong("RETURN_INVOICE_ID"));
                p.setReturnQuantity(rs.getDouble("RETURN_QUANTITY"));
                p.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                p.setSalesQuantity(rs.getDouble("SALES_QUANTITY"));
                p.setDetails(rs.getString("DETAILS"));
                p.setDate(rs.getString("DATE"));
                p.setMovementInfo(rs.getString("MOVEMENT_INFO"));
                p.setCustomerId(rs.getLong("CUSTOMER_ID"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }

    @Override
    public ArrayList<ProductMovement> findAllByKeywords(long productId, String fromDate, String toDate, long customerId) {
        String sql = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE DATE >= ? AND DATE <= ? AND  CUSTOMER_ID = ?";
        String sql2 = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE PRODUCT_ID=?  AND DATE >= ? AND DATE <=? AND  CUSTOMER_ID = ?";
        String sql3 = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE DATE BETWEEN ? AND  ?";
        ArrayList<ProductMovement> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setLong(1, productId);
            ps.setString(2, fromDate);
            ps.setString(3, toDate);
            ps.setLong(4, customerId);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductMovement p = new ProductMovement();
                p.setProductId(rs.getLong("PRODUCT_ID"));
                p.setReturnInvoiceId(rs.getLong("RETURN_INVOICE_ID"));
                p.setReturnQuantity(rs.getDouble("RETURN_QUANTITY"));
                p.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                p.setSalesQuantity(rs.getDouble("SALES_QUANTITY"));
                p.setDetails(rs.getString("DETAILS"));
                p.setDate(rs.getString("DATE"));
                p.setMovementInfo(rs.getString("MOVEMENT_INFO"));
                p.setCustomerId(rs.getLong("CUSTOMER_ID"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        System.out.println(list);
        return list;
    }
    
    @Override
    public ArrayList<ProductMovement> findAllByDateRange(String fromDate, String toDate, long productId) {
//        String sql = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE DATE >= ? AND DATE <= ? AND  CUSTOMER_ID = ?";
//        String sql2 = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE DATE BETWEEN ? AND  ? AND  CUSTOMER_ID = ?";
        String sql3 = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE DATE BETWEEN ? AND  ? AND PRODUCT_ID = ?";
        ArrayList<ProductMovement> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setLong(3, productId);
//            ps.setLong(3, customerId);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductMovement p = new ProductMovement();
                p.setProductId(rs.getLong("PRODUCT_ID"));
                p.setReturnInvoiceId(rs.getLong("RETURN_INVOICE_ID"));
                p.setReturnQuantity(rs.getDouble("RETURN_QUANTITY"));
                p.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                p.setSalesQuantity(rs.getDouble("SALES_QUANTITY"));
                p.setDetails(rs.getString("DETAILS"));
                p.setDate(rs.getString("DATE"));
                p.setMovementInfo(rs.getString("MOVEMENT_INFO"));
                p.setCustomerId(rs.getLong("CUSTOMER_ID"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        System.out.println(list);
        return list;
    }

//    @Override
//    public ArrayList<ProductMovement> findAllByKeywords(String fromDate, String toDate, long customerId) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public ArrayList<ProductMovement> findAllByCustomerId(long customerId,long productId) {
        String sql = "SELECT * FROM TB_PRODUCT_MOVEMENT WHERE CUSTOMER_ID = ? AND PRODUCT_ID = ?";
       
       
        ArrayList<ProductMovement> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setLong(1, customerId);
            ps.setLong(2, productId);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductMovement p = new ProductMovement();
                p.setProductId(rs.getLong("PRODUCT_ID"));
                p.setReturnInvoiceId(rs.getLong("RETURN_INVOICE_ID"));
                p.setReturnQuantity(rs.getDouble("RETURN_QUANTITY"));
                p.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                p.setSalesQuantity(rs.getDouble("SALES_QUANTITY"));
                p.setDetails(rs.getString("DETAILS"));
                p.setDate(rs.getString("DATE"));
                p.setMovementInfo(rs.getString("MOVEMENT_INFO"));
                p.setCustomerId(rs.getLong("CUSTOMER_ID"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        System.out.println(list);
        return list;
    }
    
    
}
