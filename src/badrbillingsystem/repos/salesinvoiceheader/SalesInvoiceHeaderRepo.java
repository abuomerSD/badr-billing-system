
package badrbillingsystem.repos.salesinvoiceheader;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.SalesInvoiceHeader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SalesInvoiceHeaderRepo implements ISalesInvoiceHeaderRepo{

    @Override
    public long save(SalesInvoiceHeader header) {
        String sql = "INSERT INTO TB_SALES_INVOICE_HEADER (CUSTOMER_ID,USER_ID,DISCOUNT,TAX,TOTAL,INVOICE_DATE) VALUES (?,?,?,?,?,?)";
        boolean status = false;
        long flag = 0;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, header.getCustomerId());
            ps.setLong(2, header.getUserId());
            ps.setDouble(3, header.getDiscount());
            ps.setDouble(4, header.getTax());
            ps.setDouble(5, header.getTotal());
            ps.setString(6, header.getDate());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            flag = keys.getLong(1);
            
//            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return flag;
    }

    @Override
    public boolean update(SalesInvoiceHeader newHeader) {
        String sql = "UPDATE TB_SALES_INVOICE_HEADER SET CUSTOMER_ID=?,USER_ID=?,DISCOUNT=?,TAX=?,TOTAL=?,INVOICE_DATE=? WHERE ID=?;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, newHeader.getCustomerId());
            ps.setLong(2, newHeader.getUserId());
            ps.setDouble(3, newHeader.getDiscount());
            ps.setDouble(4, newHeader.getTax());
            ps.setDouble(5, newHeader.getTotal());
            ps.setString(6, newHeader.getDate());
            ps.setLong(7, newHeader.getId());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM TB_SALES_INVOICE_HEADER WHERE ID=?;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public SalesInvoiceHeader findById(long id) {
        String sql = "SELECT * FROM TB_SALES_INVOICE_HEADER WHERE ID=?";
        SalesInvoiceHeader header = new SalesInvoiceHeader();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                header.setCustomerId(rs.getLong("CUSTOMER_ID"));
                header.setDate(rs.getString("INVOICE_DATE"));
                header.setDiscount(rs.getDouble("DISCOUNT"));
                header.setId(rs.getLong("ID"));
                header.setTax(rs.getDouble("TAX"));
                header.setTotal(rs.getDouble("TOTAL"));
                header.setUserId(rs.getLong("USER_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return header;
    }

    @Override
    public ArrayList<SalesInvoiceHeader> findAll() {
        String sql = "SELECT * FROM TB_SALES_INVOICE_HEADER";
        ArrayList<SalesInvoiceHeader> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                SalesInvoiceHeader header = new SalesInvoiceHeader();
                header.setCustomerId(rs.getLong("CUSTOMER_ID"));
                header.setDate(rs.getString("INVOICE_DATE"));
                header.setDiscount(rs.getDouble("DISCOUNT"));
                header.setId(rs.getLong("ID"));
                header.setTax(rs.getDouble("TAX"));
                header.setTotal(rs.getDouble("TOTAL"));
                header.setUserId(rs.getLong("USER_ID"));
                list.add(header);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }
    
}
