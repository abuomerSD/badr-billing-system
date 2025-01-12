
package badrbillingsystem.models;

public class CustomerAccount {
    private String date;
    private long salesInvoiceId;
    private long returnDocumentId;
    private double incoming;
    private double outgoing;
    private double balance;
    private double totalBalance;
    private long customerId;
    private String info;

    public CustomerAccount() {
    }

    public CustomerAccount(String date, long salesInvoiceId, long returnDocumentId, double incoming, double outgoing, double balance, double totalBalance, long customerId, String info) {
        this.date = date;
        this.salesInvoiceId = salesInvoiceId;
        this.returnDocumentId = returnDocumentId;
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.balance = balance;
        this.totalBalance = totalBalance;
        this.customerId = customerId;
        this.info = info;
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

    public long getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(long salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }

    public long getReturnDocumentId() {
        return returnDocumentId;
    }

    public void setReturnDocumentId(long returnDocumentId) {
        this.returnDocumentId = returnDocumentId;
    }

    public double getIncoming() {
        return incoming;
    }

    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    public double getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" + "date=" + date + ", salesInvoiceId=" + salesInvoiceId + ", returnDocumentId=" + returnDocumentId + ", incoming=" + incoming + ", outgoing=" + outgoing + ", balance=" + balance + ", totalBalance=" + totalBalance + ", customerId=" + customerId + ", info=" + info + '}';
    }
  
}
