
package badrbillingsystem.repos.customeraccount;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.CustomerAccount;
import badrbillingsystem.utils.AlertMaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerAccountRepo implements ICustomerAccountRepo{

    @Override
    public boolean save(CustomerAccount account) {
        String sql = "INSERT INTO TB_CUTOMER_ACCOUNT (TRANSACTION_DATE,SALES_INVOICE_ID,RETURN_INVOICE_ID,INCOMING_VALUE,OUTGOING_VALUE,TOTAL_BALANCE,INFO,CUSTOMER_ID) VALUES(?,?,?,?,?,?,?,?);";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account.getDate());
            ps.setLong(2, account.getSalesInvoiceId());
            ps.setLong(3, account.getReturnDocumentId());
            ps.setDouble(4, account.getIncoming());
            ps.setDouble(5, account.getOutgoing());
            ps.setDouble(6, account.getTotalBalance());
            ps.setString(7, account.getInfo());
            ps.setLong(8, account.getCustomerId());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return false;
    }

    @Override
    public boolean updateBySalesInvoiceId(CustomerAccount account) {
        
        String sql = "UPDATE TB_CUTOMER_ACCOUNT SET TRANSACTION_DATE=?,RETURN_INVOICE_ID=?,INCOMING_VALUE=?,OUTGOING_VALUE=?,TOTAL_BALANCE=?,INFO=? WHERE SALES_INVOICE_ID=? AND CUSTOMER_ID=? ;";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account.getDate());            
            ps.setLong(2, account.getReturnDocumentId());
            ps.setDouble(3, account.getIncoming());
            ps.setDouble(4, account.getOutgoing());
            ps.setDouble(5, account.getTotalBalance());
            ps.setString(6, account.getInfo());            
            ps.setLong(7, account.getSalesInvoiceId());
            ps.setLong(8, account.getCustomerId());
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return false;
        
    }

    @Override
    public boolean updateByReturnDocumentId(CustomerAccount account) {
        String sql = "UPDATE TB_CUTOMER_ACCOUNT SET TRANSACTION_DATE=?,SALES_INVOICE_ID=?,INCOMING_VALUE=?,OUTGOING_VALUE=?,TOTAL_BALANCE=?,INFO=? WHERE RETURN_INVOICE_ID=? AND CUSTOMER_ID=? ;";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account.getDate());            
            ps.setLong(2, account.getSalesInvoiceId());
            ps.setDouble(3, account.getIncoming());
            ps.setDouble(4, account.getOutgoing());
            ps.setDouble(5, account.getTotalBalance());
            ps.setString(6, account.getInfo());            
            ps.setLong(7, account.getReturnDocumentId());
            ps.setLong(8, account.getCustomerId());
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return false;
    }

    @Override
    public boolean deleteBySalesInvoiceId(long headId, long customerId) {
        String sql = "DELETE FROM TB_CUTOMER_ACCOUNT WHERE SALES_INVOICE_ID=? AND CUSTOMER_ID=? ;";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, headId);
            ps.setLong(2, customerId);
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return false;
    }

    @Override
    public boolean deleteByReturnDocumentId(long headId, long customerId) {
        String sql = "DELETE FROM TB_CUTOMER_ACCOUNT WHERE RETURN_INVOICE_ID=? AND CUSTOMER_ID=? ;";
       try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, headId);
            ps.setLong(2, customerId);
            System.out.println(ps.toString());
            int flag = ps.executeUpdate();
            if(flag == 1)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return false;
    }

    @Override
    public ArrayList<CustomerAccount> findByCutomerId(long id) {
        String sql = "SELECT * FROM TB_CUTOMER_ACCOUNT WHERE CUSTOMER_ID ="+ id;
        ArrayList<CustomerAccount> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            double totalBalance = 0;
            while(rs.next()) {
                CustomerAccount c = new CustomerAccount();
                
                c.setCustomerId(rs.getLong("CUSTOMER_ID"));
                c.setDate(rs.getString("TRANSACTION_DATE"));
                c.setIncoming(rs.getDouble("INCOMING_VALUE"));
                c.setInfo(rs.getString("INFO"));
                c.setOutgoing(rs.getDouble("OUTGOING_VALUE"));
                c.setReturnDocumentId(rs.getLong("RETURN_INVOICE_ID"));
                c.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                
                double newBalance = (totalBalance + c.getOutgoing()) - c.getIncoming();
                totalBalance = newBalance;
                c.setBalance(newBalance);
                c.setTotalBalance(totalBalance);
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return list;     
    }
    
    
    @Override
    public ArrayList<CustomerAccount> findByDates(String fromDate, String toDate) {
        String sql = "SELECT * FROM TB_CUTOMER_ACCOUNT WHERE TRANSACTION_DATE BETWEEN ? AND ?";
        String sql2 = "SELECT * FROM TB_CUTOMER_ACCOUNT WHERE (TRANSACTION_DATE >= date(?)) AND (TRANSACTION_DATE <= date(?))";
        ArrayList<CustomerAccount> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            double totalBalance = 0;
            while(rs.next()) {
                CustomerAccount c = new CustomerAccount();
                
                c.setCustomerId(rs.getLong("CUSTOMER_ID"));
                c.setDate(rs.getString("TRANSACTION_DATE"));
                c.setIncoming(rs.getDouble("INCOMING_VALUE"));
                c.setInfo(rs.getString("INFO"));
                c.setOutgoing(rs.getDouble("OUTGOING_VALUE"));
                c.setReturnDocumentId(rs.getLong("RETURN_INVOICE_ID"));
                c.setSalesInvoiceId(rs.getLong("SALES_INVOICE_ID"));
                
                double newBalance = (totalBalance + c.getOutgoing()) - c.getIncoming();
                totalBalance = newBalance;
                c.setBalance(newBalance);
                c.setTotalBalance(totalBalance);
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return list;     
    }
    
}
