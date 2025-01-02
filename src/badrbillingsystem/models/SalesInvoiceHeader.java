
package badrbillingsystem.models;

public class SalesInvoiceHeader {
    
    private long id;
    private long customerId;
    private long userId;
    private double discount;
    private double tax;
    private double total;
    private String date;

    public SalesInvoiceHeader() {
    }

    public SalesInvoiceHeader(long id, long customerId, long userId, double discount, double tax, double total, String date) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalesInvoiceHeader{" + "id=" + id + ", customerId=" + customerId + ", userId=" + userId + ", discount=" + discount + ", tax=" + tax + ", total=" + total + ", date=" + date + '}';
    }
    
    
    
}
