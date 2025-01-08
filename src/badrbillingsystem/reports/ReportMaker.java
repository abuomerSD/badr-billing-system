
package badrbillingsystem.reports;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.utils.AlertMaker;
import com.itextpdf.kernel.pdf.PdfDocument;
import static com.itextpdf.kernel.pdf.PdfName.BaseFont;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.pdfa.PdfADocument;
import java.awt.Font;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ReportMaker {

    public ReportMaker() {
    }
    
    public void showSalesInvoice(long id, SalesInvoiceHeader header, ArrayList<SalesInvoiceDetails> details) {
        try {
            CompanyInfoRepo companyInfoRepo = new CompanyInfoRepo();
            CompanyInfo company = companyInfoRepo.findById(1);
            
            PdfWriter pdfWriter = new PdfWriter("invoice.pdf");
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            
//            PdfPCell cCompanyName = new PdfPCell(new Paragraph("     " + company.getName() +"\n\n"
//                    + "مواد بناء - سباكة - مواد كهرباء - نجارة" + "\n\n"
//                    + "          " + company.getPhone() +"\n\n"
//                    + "          " + company.getBranch() + "\n\n" , almarai));
//            cCompanyName.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//            cCompanyName.setBorder(0);
            
            
            
            
            System.out.println("document closed");
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
}
