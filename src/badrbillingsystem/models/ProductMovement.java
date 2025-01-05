
package badrbillingsystem.models;

public class ProductMovement {
    private long productId;
    private long customerId;
    private long salesInvoiceId;
    private long returnInvoiceId;
    private double salesQuantity;
    private double returnQuantity;
    private String movementInfo;
    private String details;
    private String date;

    public ProductMovement() {
    }

    public ProductMovement(long productId, long salesInvoiceId, long returnInvoiceId, double salesQuantity, double returnQuantity, String movementInfo, String details, String date) {
        this.productId = productId;
        this.salesInvoiceId = salesInvoiceId;
        this.returnInvoiceId = returnInvoiceId;
        this.salesQuantity = salesQuantity;
        this.returnQuantity = returnQuantity;
        this.movementInfo = movementInfo;
        this.details = details;
        this.date = date;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    

    public String getMovementInfo() {
        return movementInfo;
    }

    public void setMovementInfo(String movementInfo) {
        this.movementInfo = movementInfo;
    }
    
    

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(long salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }

    public long getReturnInvoiceId() {
        return returnInvoiceId;
    }

    public void setReturnInvoiceId(long returnInvoiceId) {
        this.returnInvoiceId = returnInvoiceId;
    }

    public double getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(double salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public double getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(double returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ProductMovement{" + "productId=" + productId + ", salesInvoiceId=" + salesInvoiceId + ", returnInvoiceId=" + returnInvoiceId + ", salesQuantity=" + salesQuantity + ", returnQuantity=" + returnQuantity + ", details=" + details + '}';
    }

    
    
    

}