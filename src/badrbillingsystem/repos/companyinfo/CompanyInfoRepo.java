
package badrbillingsystem.repos.companyinfo;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.CompanyInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CompanyInfoRepo implements ICompanyInfoRepo{

    @Override
    public boolean save(CompanyInfo companyInfo) {
        String sql = "INSERT INTO TB_COMPANY_INFO (NAME,LOGO,PHONE,ADDRESS,TAX_NUMBER,BRANCH,QR_CODE,INSTRUCTIONS) VALUES (?,?,?,?,?,?,?,?);";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, companyInfo.getName());
            ps.setString(2, companyInfo.getLogo());
            ps.setString(3, companyInfo.getPhone());
            ps.setString(4, companyInfo.getAddress());
            ps.setString(5, companyInfo.getTaxNumber());
            ps.setString(6, companyInfo.getBranch());
            ps.setString(7, companyInfo.getQrCode());
            ps.setString(8, companyInfo.getInstructions());
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(CompanyInfo newCompanyInfo) {
        String sql = "UPDATE TB_COMPANY_INFO SET NAME=?,LOGO=?,PHONE=?,ADDRESS=?,TAX_NUMBER=?,BRANCH=?,QR_CODE=?,INSTRUCTIONS=? WHERE ID=1;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newCompanyInfo.getName());
            ps.setString(2, newCompanyInfo.getLogo());
            ps.setString(3, newCompanyInfo.getPhone());
            ps.setString(4, newCompanyInfo.getAddress());
            ps.setString(5, newCompanyInfo.getTaxNumber());
            ps.setString(6, newCompanyInfo.getBranch());
            ps.setString(7, newCompanyInfo.getQrCode());
            ps.setString(8, newCompanyInfo.getInstructions());
//            ps.setLong(9, newCompanyInfo.getId());
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return status;
    }

//    @Override
//    public boolean delete(long id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public CompanyInfo findById(long id) {
        String sql = "SELECT * FROM TB_COMPANY_INFO WHERE ID=1;";
        CompanyInfo c = new CompanyInfo();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            c.setAddress(rs.getString("ADDRESS"));
            c.setId(rs.getLong("ID"));
            c.setLogo(rs.getString("LOGO"));
            c.setName(rs.getString("NAME"));
            c.setPhone(rs.getString("PHONE"));
            c.setBranch(rs.getString("BRANCH"));
            c.setInstructions(rs.getString("INSTRUCTIONS"));
            c.setQrCode(rs.getString("QR_CODE"));
            c.setTaxNumber(rs.getString("TAX_NUMBER"));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return c;
    }

    @Override
    public CompanyInfo findByName(String name) {
        String sql = "SELECT * FROM TB_COMPANY_INFO WHERE NAME=?;";
        CompanyInfo c = new CompanyInfo();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            c.setAddress(rs.getString("ADDRESS"));
            c.setId(rs.getLong("ID"));
            c.setLogo(rs.getString("LOGO"));
            c.setName(name);
            c.setPhone(rs.getString("PHONE"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return c;
    }

    @Override
    public ArrayList<CompanyInfo> findAll() {
        String sql = "SELECT * FROM TB_COMPANY_INFO;"; 
        ArrayList<CompanyInfo> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                CompanyInfo c = new CompanyInfo(rs.getLong("ID"),
                                        rs.getString("NAME"),
                                        rs.getString("LOGO"),
                                        rs.getString("PHONE"),
                                        rs.getString("ADDRESS"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }

    @Override
    public ArrayList<CompanyInfo> findBySearchWords(String searchWords) {
        String sql = "SELECT * FROM TB_COMPANY_INFO WHERE NAME LIKE '%" + searchWords +"%'";
        ArrayList<CompanyInfo> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                CompanyInfo c = new CompanyInfo();
                c.setAddress(rs.getString("ADDRESS"));
                c.setId(rs.getLong("ID"));
                c.setLogo(rs.getString("LOGO"));
                c.setName(rs.getString("NAME"));
                c.setPhone(rs.getString("PHONE"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }
    
}
