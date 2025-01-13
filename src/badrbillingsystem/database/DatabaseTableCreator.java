
package badrbillingsystem.database;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.User;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.repos.user.UserRepo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class DatabaseTableCreator {
    
    public static void createTables() {
        createCustomersTable();
        createCustomerAccountTable();
        createProductsTable();
        createUsersTable();
        insertAdminUser();
        createCompanyInfoTable();
        createSalesInvoiceHeaderTable();
        createSalesInvoiceDetailsTable();
        createProductMovementTable();
        createReturnDocumentHeaderTable();
        createReturnDocumentDetailsTable();
        insertDefaultCompanyInfo();
    }
    
    private static void createCustomersTable(){
        String sql = "CREATE TABLE  IF NOT EXISTS TB_CUSTOMER (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	NAME VARCHAR(100) UNIQUE, \n" +
                            "	PHONE VARCHAR(100)\n" +
                            ") ;";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    private static void createProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_PRODUCT (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	NAME VARCHAR(100) UNIQUE,\n" +
                            "	PRICE DOUBLE, \n" +
                            "	IMAGE VARCHAR(100)\n" +
                            ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }

    private static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_USER (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	USERNAME VARCHAR(30) UNIQUE,\n" +
                            "	PASSWORD VARCHAR(50)\n" +
                            ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void insertAdminUser() {
        UserRepo repo = new UserRepo();
        ArrayList<User> list = repo.findAll();
        
        if(list.size() > 0 ) return;
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            User u = new User();
            u.setUsername("admin");
            u.setPassword("12345");
            repo.save(u);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createCompanyInfoTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_COMPANY_INFO(\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	NAME VARCHAR(100),\n" +
                            "	LOGO VARCHAR(100),\n" +
                            "	PHONE VARCHAR(50),\n" +
                            "	TAX_NUMBER VARCHAR(50),\n" +
                            "	BRANCH VARCHAR(50),\n" +
                            "	QR_CODE VARCHAR(100),\n" +
                            "	INSTRUCTIONS VARCHAR(100),\n" +
                            "	ADDRESS VARCHAR(100)\n" +
                            ");";
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createSalesInvoiceHeaderTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_SALES_INVOICE_HEADER (\n" +
                                "ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                "CUSTOMER_ID INTEGER,\n" +
                                "USER_ID INTEGER,\n" +
                                "DISCOUNT DOUBLE,\n" +
                                "TAX DOUBLE,\n" +
                                "TOTAL DOUBLE,\n" +
                                "INVOICE_DATE VARCHAR(50)\n" +
                                "\n" +
                                ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createSalesInvoiceDetailsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_SALES_INVOICE_DETAILS (\n" +
                                "HEADER_ID INTEGER,\n" +
                                "PRODUCT_ID INTEGER,\n" +
                                "QUANTITY DOUBLE,\n" +
                                "PRICE DOUBLE,\n" +
                                "ITEM_TOTAL DOUBLE,\n" +
                                "DETAILS VARCHAR(100)\n" +
                                ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createProductMovementTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_PRODUCT_MOVEMENT(\n" +
                                "PRODUCT_ID INTEGER,\n" +
                                "CUSTOMER_ID INTEGER,\n" +
                                "SALES_INVOICE_ID INTEGER,\n" +
                                "RETURN_INVOICE_ID INTEGER,\n" +
                                "SALES_QUANTITY DOUBLE,\n" +
                                "RETURN_QUANTITY DOUBLE,\n" +
                                "MOVEMENT_INFO VARCHAR(50),\n" +
                                "DETAILS VARCHAR(100),\n" +
                                "DATE VARCHAR(50)\n" +
                                ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createReturnDocumentHeaderTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_RETURN_DOCUMENT_HEADER(\n" +
                                "ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                                "CUSTOMER_ID INTEGER,\n" +
                                "SALES_INVOICE_ID INTEGER,\n" +
                                "DETAILS VARCHAR(100),\n" +
                                "TOTAL DOUBLE,\n" +
                                "DOCUMENT_DATE VARCHAR(50)\n" +
                                ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createReturnDocumentDetailsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_RETURN_DOCUMENT_DETAILS(\n" +
                                "HEADER_ID INTEGER,\n" +
                                "PRODUCT_ID INTEGER,\n" +
                                "QUANTITY DOUBLE,\n" +
                                "DETAILS VARCHAR(100)\n" +
                                ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void insertDefaultCompanyInfo() {
        try {
            CompanyInfoRepo repo = new CompanyInfoRepo();
            CompanyInfo info = new CompanyInfo();
            info.setAddress("المدينة المنورة الحرة الشرقية - طريق الملك عبد العزيز");
            info.setBranch("الفرع الرئيسي");
            info.setInstructions("البضاعة المباعة لا ترد و لا تستبدل الا بحضور الفاتورة الاصل. * العدد اليدوية لا ترد و لا تستبدل * الدينموهات و الغطاسات و اوتوماتيك دينمو ضمان الشركة و ليس المحل * الدريلات و الصواريخ و الخلاطات و الشطافات ضمان الشركة * التوصيلات الكهربائية و اللمبات و مراوح الشفط ضمان الشركة");
            info.setLogo("src/badrbillingsystem/resources/images/smalllogo.png");
            info.setName("مؤسسة بن سعد للتجارة");
            info.setPhone("0539068795");
            info.setQrCode("src/badrbillingsystem/resources/images/smallqr.png");
            info.setTaxNumber("300414463400003");
            
            ArrayList<CompanyInfo> list = repo.findAll();
//            repo.save(info);
            if(list.size() == 0 ) {
                repo.save(info);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void createCustomerAccountTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_CUTOMER_ACCOUNT (\n" +
                                    "TRANSACTION_DATE VARCHAR(50),\n" +
                                    "SALES_INVOICE_ID INTEGER,\n" +
                                    "RETURN_INVOICE_ID INTEGER,\n" +
                                    "CUSTOMER_ID INTEGER,\n" +
                                    "INCOMING_VALUE DOUBLE,\n" +
                                    "OUTGOING_VALUE DOUBLE,\n" +
                                    "TOTAL_BALANCE DOUBLE,\n" +
                                    "INFO VARCHAR(50)\n" +
                                    ");";
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}
