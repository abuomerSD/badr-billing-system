
package badrbillingsystem.models;


public class ReturnDocumentDetails {
    private long headerId;
    private long productId;
    private double quantity;
    private String details;

    public ReturnDocumentDetails() {
    }

    public ReturnDocumentDetails(long headerId, long productId, double quantity, String details) {
        this.headerId = headerId;
        this.productId = productId;
        this.quantity = quantity;
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
