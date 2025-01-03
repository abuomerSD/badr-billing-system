
package badrbillingsystem.models;

public class ReturnDocumentHeader {
    private long id;
    private long salesInvoiceId;
    private String details;
    private String date;

    public ReturnDocumentHeader() {
    }

    public ReturnDocumentHeader(long id, long salesInvoiceId, String details, String date) {
        this.id = id;
        this.salesInvoiceId = salesInvoiceId;
        this.details = details;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(long salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReturnDocumentHeader{" + "id=" + id + ", salesInvoiceId=" + salesInvoiceId + ", details=" + details + ", date=" + date + '}';
    }
    
    
}
