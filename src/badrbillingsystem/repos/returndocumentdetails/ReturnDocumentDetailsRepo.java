
package badrbillingsystem.repos.returndocumentdetails;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.ReturnDocumentDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ReturnDocumentDetailsRepo implements IReturnDocumentDetailsRepo{

    @Override
    public boolean save(ReturnDocumentDetails details) {
        String sql = "INSERT INTO TB_RETURN_DOCUMENT_DETAILS(HEADER_ID,PRODUCT_ID,QUANTITY,DETAILS) VALUES (?,?,?,?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, details.getHeaderId());
            ps.setLong(2, details.getProductId());
            ps.setDouble(3, details.getQuantity());
            ps.setString(4, details.getDetails());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }

    @Override
    public boolean update(ReturnDocumentDetails newDetails, long productId) {
        String sql = "UPDATE TB_RETURN_DOCUMENT_DETAILS SET QUANTITY=?,DETAILS=? WHERE HEADER_ID=? AND PRODUCT_ID=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setDouble(1, newDetails.getQuantity());
            ps.setString(2, newDetails.getDetails());
            ps.setLong(3, newDetails.getHeaderId());
            ps.setLong(4, newDetails.getProductId());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(long headerId, long productId) {
        String sql = "DELETE FROM TB_RETURN_DOCUMENT_DETAILS WHERE HEADER_ID=? AND PRODUCT_ID=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, headerId);
            ps.setLong(2, productId);
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }

    @Override
    public ArrayList<ReturnDocumentDetails> findAllByHeaderId(long id) {
        String sql = "SELECT * FROM TB_RETURN_DOCUMENT_DETAILS WHERE HEADER_ID=?";
        ArrayList<ReturnDocumentDetails> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ReturnDocumentDetails d = new ReturnDocumentDetails();
                d.setDetails(rs.getString("DETAILS"));
                d.setHeaderId(rs.getLong("HEADER_ID"));
                d.setProductId(rs.getLong("PRODUCT_ID"));
                d.setQuantity(rs.getDouble("QUANTITY"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }


    
}
