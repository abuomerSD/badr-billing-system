
package badrbillingsystem.repos.returndocumentheader;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.ReturnDocumentHeader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ReturnDocumentHeaderRepo implements IReturnDocumentHeaderRepo{

    @Override
    public boolean save(ReturnDocumentHeader header) {
        String sql = "INSERT INTO TB_RETURN_DOCUMENT_HEADER (SALES_INVOICE_ID,DETAILS,DOCUMENT_DATE) VALUES (?,?,?)";
        boolean status  = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, header.getSalesInvoiceId());
            ps.setString(2, header.getDetails());
            ps.setString(3, header.getDate());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(ReturnDocumentHeader newHeader) {
        String sql = "UPDATE TB_RETURN_DOCUMENT_HEADER SET SALES_INVOICE_ID=?,DETAILS=?,DOCUMENT_DATE=? WHERE ID=?";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, newHeader.getId());
            ps.setString(2, newHeader.getDetails());
            ps.setString(3, newHeader.getDate());
            ps.setLong(4, newHeader.getId());
            int flag = ps.executeUpdate();
            if(flag == 1) 
                return true;
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM TB_RETURN_DOCUMENT_HEADER WHERE ID=?";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);           
            int flag = ps.executeUpdate();
            if(flag == 1) 
                return true;
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return false;
    }

    @Override
    public ReturnDocumentHeader findById(long id) {
        String sql = "SELECT * FROM TB_RETURN_DOCUMENT_HEADER WHERE ID = ?";
        ReturnDocumentHeader h = new ReturnDocumentHeader();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                h.setDate(rs.getString("DOCUMENT_DATE"));
                h.setDetails(rs.getString("DETAILS"));
                h.setId(rs.getLong("ID"));
                h.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return h;
    }

    @Override
    public ArrayList<ReturnDocumentHeader> findAll() {
        String sql = "SELECT * FROM TB_RETURN_DOCUMENT_HEADER";
        ArrayList<ReturnDocumentHeader> list = new ArrayList<ReturnDocumentHeader>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ReturnDocumentHeader h = new ReturnDocumentHeader();
                h.setDate(rs.getString("DOCUMENT_DATE"));
                h.setDetails(rs.getString("DETAILS"));
                h.setId(rs.getLong("ID"));
                h.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                list.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }
    
}
