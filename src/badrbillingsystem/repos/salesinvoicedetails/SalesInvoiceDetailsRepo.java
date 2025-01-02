
package badrbillingsystem.repos.salesinvoicedetails;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.SalesInvoiceDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SalesInvoiceDetailsRepo implements ISalesInvoiceDetailsRepo{

    @Override
    public boolean save(SalesInvoiceDetails details) {
        String sql = "INSERT INTO TB_SALES_INVOICE_DETAILS (HEADER_ID,PRODUCT_ID,QUANTITY,PRICE,ITEM_TOTAL,DETAILS) VALUES(?,?,?,?,?,?)";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, details.getHeaderId());
            ps.setLong(2, details.getProductId());
            ps.setDouble(3, details.getQuantity());
            ps.setDouble(4, details.getPrice());
            ps.setDouble(5, details.getTotal());
            ps.setString(6, details.getDetails());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(SalesInvoiceDetails newDetails) {
        String sql = "UPDATE TB_SALES_INVOICE_DETAILS SET QUANTITY=?,PRICE=?,ITEM_TOTAL=?,DETAILS=? WHERE PRODUCT_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);  
            ps.setDouble(1, newDetails.getQuantity());
            ps.setDouble(2, newDetails.getPrice());
            ps.setDouble(3, newDetails.getTotal());
            ps.setString(4, newDetails.getDetails());
            ps.setLong(5, newDetails.getProductId());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean delete(long productId) {
        String sql = "DELETE FROM TB_SALES_INVOICE_DETAILS WHERE PRODUCT_ID=?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, productId);
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public ArrayList<SalesInvoiceDetails> findAllByHeaderId(long id) {
        String sql = "SELECT * FROM TB_SALES_INVOICE_DETAILS WHERE HEADER_ID=?";
        ArrayList<SalesInvoiceDetails> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                SalesInvoiceDetails details = new SalesInvoiceDetails();
                details.setDetails(rs.getString("DETAILS"));
                details.setHeaderId(rs.getLong("HEADER_ID"));
                details.setPrice(rs.getDouble("PRICE"));
                details.setProductId(rs.getLong("PRODUCT_ID"));
                details.setQuantity(rs.getDouble("QUANTITY"));
                details.setTotal(rs.getDouble("ITEM_TOTAL"));
                list.add(details);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
        
    }
    
}
