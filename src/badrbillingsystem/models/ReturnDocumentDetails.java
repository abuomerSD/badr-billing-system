
package badrbillingsystem.models;


public class ReturnDocumentDetails {
    private long headerId;
    private long productId;
    private String productName;
    private double quantity;
    private double price;
    private double total;
    private String details;

    public ReturnDocumentDetails() {
    }

    public ReturnDocumentDetails(long headerId, long productId, double quantity, String details) {
        this.headerId = headerId;
        this.productId = productId;
        this.quantity = quantity;
        this.details = details;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ReturnDocumentDetails{" + "headerId=" + headerId + ", productId=" + productId + ", quantity=" + quantity + ", details=" + details + '}';
    }
    
    
}
