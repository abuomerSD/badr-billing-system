
package badrbillingsystem.reports;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.utils.AlertMaker;
import com.itextpdf.text.Document;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportMaker {

    public ReportMaker()  {
        
    }
    
    
            DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
    
    
    
    public void showSalesInvoice(long id, SalesInvoiceHeader header, ArrayList<SalesInvoiceDetails> details, Customer c) {
        try {
            CompanyInfoRepo companyInfoRepo = new CompanyInfoRepo();
            CompanyInfo company = companyInfoRepo.findById(1);
            
            // font 
            String almaraiFontPath = "/badrbillingsystem/resources/fonts/Almarai-Bold.ttf";
            BaseFont base = BaseFont.createFont(almaraiFontPath,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font almarai = new Font(base, 9);

//            FontProgram fontProgram = FontProgramFactory.createFont(almaraiFontPath);
//            PdfFont almarai = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, true);
            
            String outputFileName = "./docs/" + "sales-invoice-"+id+".pdf";
            Document document = new Document(PageSize.A4);
            
            PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
                        
            document.open();  
            System.out.println("document opened");
            float fullPageWidth[] = {570f};
            float twoColWidth[] = {285f, 285f};
            float threeColWidth[] = {190f, 190f, 190f};
            float fiveColWidth[] = {114f,114f,114f,114f,114f};
            float sixcolWidth[] = {95f,95f,95f,95f,95f,95f};
            
            // company info , logo and qr code
            
            PdfPTable tbCompanyInfoAndLogos = new PdfPTable(threeColWidth);
            
            
            // company info
            
            PdfPCell companyInfo = getUnborderdCell(company.getName(), almarai);
            
            // logo
            Image logo = Image.getInstance(company.getLogo());
            logo.setBorder(-1);
//            System.out.println(logo.getBorder());
            logo.scaleToFit(20,20);
            logo.setPaddingTop(30);
            
            
            // qr code
            Image qrCode = Image.getInstance(company.getQrCode());
            qrCode.setBorder(0);
            
            
            // add to table
            tbCompanyInfoAndLogos.addCell(qrCode);
            tbCompanyInfoAndLogos.addCell(logo);
            tbCompanyInfoAndLogos.addCell(companyInfo);
            
            document.add(tbCompanyInfoAndLogos);
            
            
            // line break
            Paragraph newLine = new Paragraph("\n");
            document.add(newLine);
            PdfPTable lineBreak = new PdfPTable(fullPageWidth);
            PdfPCell lineBreakcCell = getborderdCell("", almarai);
            lineBreakcCell.setBorderWidth(1);
            lineBreak.addCell(lineBreakcCell);
//            document.add(lineBreak);
            
            
            // address 
            PdfPTable address = new PdfPTable(fullPageWidth);
            PdfPCell addressCell = getUnborderdCell(company.getAddress()+ " - " + "هاتف " + company.getPhone(), almarai);
            addressCell.setHorizontalAlignment(1);
            addressCell.setBorder(3);
            
            address.addCell(addressCell);
            address.setSpacingBefore(2);

            document.add(address);
            
            
            // date and invoice id
            float d[] = {700f,700f,700f};
            PdfPTable date = new PdfPTable(d);
            
            PdfPCell dateCell = getborderdCell( "التاريخ"+ "    "+header.getDate() + "  " + "Date", almarai);
            
            PdfPCell titleCell = getUnborderdCell("فاتورة ضريبية" + " " + "Tax Invoice", almarai);
            titleCell.setPadding(5);
            
            PdfPCell invoiceIdCell = getborderdCell("رقم الفاتورة" + "  " + header.getId() + "  " +"Invoice", almarai);

            
            date.addCell(invoiceIdCell);
            date.addCell(titleCell);
            date.addCell(dateCell);
            date.setSpacingBefore(3);
            document.add(date);
            
            // cutomer name 
            PdfPTable customer = new PdfPTable(fullPageWidth);
            PdfPTable customerWithoutBorder = new PdfPTable(threeColWidth);
            
            PdfPCell cutomerNameEnglishLbCell = getUnborderdCell("Customer Name", almarai);
            cutomerNameEnglishLbCell.setRunDirection(PdfWriter.RUN_DIRECTION_NO_BIDI);            
            PdfPCell cutomerNameCell = getUnborderdCell(header.getCutomerName(), almarai);
            PdfPCell cutomerNameArabicLBCell = getUnborderdCell("اسم العميل", almarai);
            cutomerNameCell.setHorizontalAlignment(1);
            
            customerWithoutBorder.addCell(cutomerNameEnglishLbCell);
            customerWithoutBorder.addCell(cutomerNameCell);
            customerWithoutBorder.addCell(cutomerNameArabicLBCell);
            
            customer.addCell(customerWithoutBorder);
            customer.setSpacingBefore(2);
            document.add(customer);
            
            // customer tax and phone number 
            PdfPTable cutomerTaxAndPhone = new PdfPTable(fullPageWidth);
            PdfPTable cutomerTaxAndPhoneSixCols = new PdfPTable(sixcolWidth);
            
            PdfPCell phoneEngTextCell = getUnborderdCell("Phone", almarai);
            phoneEngTextCell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell phoneCell = getUnborderdCell(c.getPhone(), almarai);
            PdfPCell phoneArTextCell = getUnborderdCell("رقم الهاتف", almarai);
            PdfPCell customerTaxEngTextCell = getUnborderdCell("Customer Tax", almarai);
            PdfPCell customerTaxCell = getUnborderdCell("", almarai);
            PdfPCell customerArEngTextCell = getUnborderdCell("الرقم الضريبي", almarai);
            
            cutomerTaxAndPhoneSixCols.addCell(phoneEngTextCell);
            cutomerTaxAndPhoneSixCols.addCell(phoneCell);
            cutomerTaxAndPhoneSixCols.addCell(phoneArTextCell);
            cutomerTaxAndPhoneSixCols.addCell(customerTaxEngTextCell);
            cutomerTaxAndPhoneSixCols.addCell(customerTaxCell);
            cutomerTaxAndPhoneSixCols.addCell(customerArEngTextCell);
            
            cutomerTaxAndPhone.addCell(cutomerTaxAndPhoneSixCols);
            
            cutomerTaxAndPhone.setSpacingBefore(2);
            document.add(cutomerTaxAndPhone);
            
            // customer address 
            PdfPTable customerAddressOuterTb = new PdfPTable(fullPageWidth);
            PdfPTable customerAddressInnerTb = new PdfPTable(fullPageWidth);
            PdfPTable customerAddressTb = new PdfPTable(threeColWidth);
            
            PdfPCell customerAddress1Cell = getUnborderdCell("Customer Address", almarai);
            customerAddress1Cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell customerAddress3Cell = getUnborderdCell("-", almarai);
            PdfPCell customerAddress6Cell = getUnborderdCell("العنوان", almarai);
            
            customerAddressTb.addCell(customerAddress1Cell);
            customerAddressTb.addCell(customerAddress3Cell);
            customerAddressTb.addCell(customerAddress6Cell);
             
            customerAddressInnerTb.addCell(customerAddressTb);
            
            PdfPCell ccc = new PdfPCell(customerAddressInnerTb);
            ccc.setBorder(0);
            ccc.setPadding(3);
            customerAddressOuterTb.addCell(customerAddressTb);
            customerAddressOuterTb.setSpacingBefore(2);
            
            document.add(customerAddressOuterTb);
            
            // invoice type 
            
            PdfPTable invoiceTypeOuterTb = new PdfPTable(fullPageWidth);
            PdfPTable invoiceTypeInnerTb = new PdfPTable(sixcolWidth);
            double totalPieces = 0;
            
            for (SalesInvoiceDetails detail : details) {
                totalPieces = totalPieces +  detail.getQuantity();
            }
            
            PdfPCell invoiceTypeCell1 = getUnborderdCell(decimalFormat.format(totalPieces), almarai);
            invoiceTypeCell1.setHorizontalAlignment(1);
            PdfPCell invoiceTypeCell2 = getUnborderdCell("عدد القطع", almarai);
            PdfPCell invoiceTypeCell3 = getUnborderdCell("", almarai);
            PdfPCell invoiceTypeCell4 = getUnborderdCell("", almarai);
            PdfPCell invoiceTypeCell5 = getUnborderdCell("مدفوع", almarai);
            PdfPCell invoiceTypeCell6 = getUnborderdCell("حالة الفاتورة", almarai);
            
            invoiceTypeInnerTb.addCell(invoiceTypeCell1);
            invoiceTypeInnerTb.addCell(invoiceTypeCell2);
            invoiceTypeInnerTb.addCell(invoiceTypeCell3);
            invoiceTypeInnerTb.addCell(invoiceTypeCell4);
            invoiceTypeInnerTb.addCell(invoiceTypeCell5);
            invoiceTypeInnerTb.addCell(invoiceTypeCell6);
            
            invoiceTypeOuterTb.addCell(invoiceTypeInnerTb);
            invoiceTypeOuterTb.setSpacingBefore(2);
            
            document.add(invoiceTypeOuterTb);
            
            document.close();
            System.out.println("document closed");

            
//            PdfPCell cCompanyName = new PdfPCell(new Paragraph("     " + company.getName() +"\n\n"
//                    + "مواد بناء - سباكة - مواد كهرباء - نجارة" + "\n\n"
//                    + "          " + company.getPhone() +"\n\n"
//                    + "          " + company.getBranch() + "\n\n" , almarai));
//            cCompanyName.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//            cCompanyName.setBorder(0);
            
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    
    
    PdfPCell getUnborderdCell(String text, Font font){
        Paragraph paragraph = new Paragraph(text, font);
//        paragraph.setFont(almarai);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(0);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        
        return cell;
    }
    
    PdfPCell getborderdCell(String text, Font font){
        Paragraph paragraph = new Paragraph(text, font);
//        paragraph.setFont(almarai);
        PdfPCell cell = new PdfPCell(paragraph);
//        cell.setBorder(18);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        
        return cell;
    }
    
    
//    BaseFont getBaseFont() throws DocumentException, IOException{
//        String almaraiFontPath = "/badrbillingsystem/resources/fonts/Almarai-Bold.ttf";
//        return BaseFont.createFont(almaraiFontPath,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//    }
    
}
