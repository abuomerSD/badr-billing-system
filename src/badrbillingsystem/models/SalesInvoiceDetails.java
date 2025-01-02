
package badrbillingsystem.models;


public class SalesInvoiceDetails {
    private long headerId;
    private long productId;
    private double quantity;
    private double price;
    private double total;
    private String details;

    public SalesInvoiceDetails() {
    }

    public SalesInvoiceDetails(long headerId, long productId, double quantity, double price, double total, String details) {
        this.headerId = headerId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.details = details;
    }

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "SalesInvoiceDetails{" + "headerId=" + headerId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + ", total=" + total + ", details=" + details + '}';
    }
    
    
}
