
package badrbillingsystem.repos.productmovement;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.ProductMovement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductMovementRepo implements IProductMovementRepo{

    @Override
    public boolean save(ProductMovement productMovement) {
        String sql = "INSERT INTO TB_PRODUCT_MOVEMENT (PRODUCT_ID,SALES_INVOICE_ID,RETURN_INVOICE_ID,SALES_QUANTITY,RETURN_QUANTITY) VALUES (?,?,?,?,?)";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, productMovement.getProductId());
            ps.setLong(2, productMovement.getSalesInvoiceId());
            ps.setLong(3, productMovement.getReturnInvoiceId());
            ps.setDouble(4, productMovement.getSalesQuantity());
            ps.setDouble(5, productMovement.getReturnQuantity());
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
        String sql = "UPDATE TB_PRODUCT_MOVEMENT SET SALES_QUANTITY=? WHERE PRODUCT_ID=? AND SALES_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);            
            ps.setDouble(1, newProductMovement.getSalesQuantity());
            ps.setLong(2, newProductMovement.getProductId());
            ps.setLong(3, newProductMovement.getSalesInvoiceId());
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
        String sql = "UPDATE TB_PRODUCT_MOVEMENT SET RETURN_QUANTITY=? WHERE PRODUCT_ID=? AND RETURN_INVOICE_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);  
            ps.setDouble(1, newProductMovement.getReturnQuantity());
            ps.setLong(2, newProductMovement.getProductId());
            ps.setLong(3, newProductMovement.getReturnInvoiceId());    
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
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }
    
}
