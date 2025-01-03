
package badrbillingsystem.models;

public class ProductMovement {
    private long productId;
    private long salesInvoiceId;
    private long returnInvoiceId;
    private double salesQuantity;
    private double returnQuantity;

    public ProductMovement() {
    }

    public ProductMovement(long productId, long salesInvoiceId, long returnInvoiceId, double salesQuantity, double returnQuantity) {
        this.productId = productId;
        this.salesInvoiceId = salesInvoiceId;
        this.returnInvoiceId = returnInvoiceId;
        this.salesQuantity = salesQuantity;
        this.returnQuantity = returnQuantity;
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

    @Override
    public String toString() {
        return "ProductMovement{" + "productId=" + productId + ", salesInvoiceId=" + salesInvoiceId + ", returnInvoiceId=" + returnInvoiceId + ", salesQuantity=" + salesQuantity + ", returnQuantity=" + returnQuantity + '}';
    }
    
    

}