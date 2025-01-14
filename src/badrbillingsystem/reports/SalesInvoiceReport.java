
package badrbillingsystem.reports;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.Constants;
import badrbillingsystem.utils.NumberConvertToArabicText;
import com.itextpdf.text.BaseColor;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SalesInvoiceReport {

    public SalesInvoiceReport()  {
        
    }
    
    
    DecimalFormat decimalFormat = new DecimalFormat("#,###,###.##");
    
    float fullPageWidth[] = {570f};
    float oneColWidth[] = {190f};
    float twoColWidth[] = {285f, 285f};
    float twoColSpectialWidth[] = {300f, 300f};
    float threeColWidth[] = {190f, 190f, 190f};
    float threeColSpecialWidth[] = {90f, 90f, 90f};
    float fiveColWidth[] = {114f,114f,114f,114f,114f};
    float sixcolWidth[] = {95f,95f,95f,95f,95f,95f};
    float productsTableWidth[] = {63f,63f,63f,63f,63f,189f,63f};
    float speratorWidth[] = {20f};
    float specialWidth[] = {275f,20f,275f};
    
    String fiveTabs= "                  ";
    String fourTabs = "             ";
    String threeTabs = "         ";
    
    String almaraiFontRegular = "/badrbillingsystem/resources/fonts/Almarai-Bold.ttf";
    String almaraiFontBold = "/badrbillingsystem/resources/fonts/Almarai-ExtraBold.ttf";
    
    public String saveSalesInvoiceAsPdf(long id, SalesInvoiceHeader header, ArrayList<SalesInvoiceDetails> details, Customer c) {
        String invoiceName = "";
        try {
            
            int pageNumber = 1;
            
            CompanyInfoRepo companyInfoRepo = new CompanyInfoRepo();
            CompanyInfo company = companyInfoRepo.findById(1);
            
            // font 
            
            BaseFont base = BaseFont.createFont(almaraiFontRegular,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font almarai = new Font(base, 9);

//            FontProgram fontProgram = FontProgramFactory.createFont(almaraiFontRegular);
//            PdfFont almarai = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, true);
            
            String outputFileName = Constants.SALES_INVOICE_SUFFIX + id + Constants.SALES_INVOICE_EXTENTION;
            invoiceName = outputFileName;
            Document document = new Document(PageSize.A4);
            
            PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
                        
            document.open();  
            System.out.println("document opened");
       
           // products Details 
           
           double tax = header.getTax();
           double invoiceTotal = header.getTotal();
           double invoiceDiscount = header.getDiscount();
           
           double oldTotalWithoutTaxAndDiscount = (invoiceTotal + tax) - invoiceDiscount;
           double oldTotalWithoutTaxAndDiscount1 = (invoiceTotal - tax) + invoiceDiscount;
           
//           double taxPercentage = tax / oldTotalWithoutTaxAndDiscount;
           double taxPercentage = tax / oldTotalWithoutTaxAndDiscount1;
//           double taxPercentage = (tax / invoiceTotal - invoiceDiscount) ;

            addPageHeader(document, id, header, details, c, pageNumber);
           int productsPerPage = 17;
           int rowNumber = 1;
           int productNumber = 1;
            for (SalesInvoiceDetails detail : details) {
                if(rowNumber == productsPerPage + 1) {
                    pageNumber++;
                    addPageFooter(document, id, header, details, c, pageNumber);
                    document.newPage();
                    addPageHeader(document, id, header, details, c, pageNumber);
                    rowNumber = 1;
                    
                }
                PdfPTable productsDetailsOuterTable = new PdfPTable(fullPageWidth);
//                PdfPTable productsDetailsInnerTable = new PdfPTable(fullPageWidth);
                PdfPTable productsDetailsInnerTable = new PdfPTable(productsTableWidth);
                
                double pPrice = detail.getPrice();
                double ptax = pPrice * taxPercentage;
                double pPriceWithTax = ptax+ pPrice;
                double pTotal = pPriceWithTax * detail.getQuantity();
                
                PdfPCell pdTotalCell = getUnborderdCell(decimalFormat.format(pTotal), almarai);
                pdTotalCell.setHorizontalAlignment(1);
                PdfPCell pdQuantityCell = getUnborderdCell(decimalFormat.format(detail.getQuantity()), almarai);
                pdQuantityCell.setHorizontalAlignment(1);
                
                PdfPCell pdPriceWithTaxCell = getUnborderdCell(decimalFormat.format(pPriceWithTax), almarai);
                pdPriceWithTaxCell.setHorizontalAlignment(1);
                PdfPCell pdTaxCell = getUnborderdCell(decimalFormat.format(ptax), almarai);
                pdTaxCell.setHorizontalAlignment(1);
                PdfPCell pdPriceCell = getUnborderdCell(decimalFormat.format(pPrice), almarai);
                pdPriceCell.setHorizontalAlignment(1);
                PdfPCell pdNameCell = getUnborderdCell(detail.getProductName(), almarai);
                pdNameCell.setHorizontalAlignment(1);
//                pdNameCell.setPadding(2);
                PdfPCell pdNoCell = getUnborderdCell(String.valueOf(productNumber), almarai);
                pdNoCell.setHorizontalAlignment(1);
                
                productsDetailsInnerTable.addCell(pdTotalCell);
                productsDetailsInnerTable.addCell(pdQuantityCell);
                productsDetailsInnerTable.addCell(pdPriceWithTaxCell);
                productsDetailsInnerTable.addCell(pdTaxCell);
                productsDetailsInnerTable.addCell(pdPriceCell);
                productsDetailsInnerTable.addCell(pdNameCell);
                productsDetailsInnerTable.addCell(pdNoCell);
                
                productsDetailsOuterTable.addCell(productsDetailsInnerTable);
//                productsDetailsOuterTable.setPaddingTop(10f);
                
                document.add(productsDetailsOuterTable);
                
                rowNumber++;
                productNumber++;
            }
           
           
//           addPageHeader(document, id, header, details, c, pageNumber);
           
           
            addPageFooter(document, id, header, details, c, pageNumber);
           
            
            // close the document
            document.close();
            System.out.println("document closed");

            

            
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        return invoiceName;
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
        paragraph.setLeading(10f, 10f);
//        paragraph.setFont(almarai);
        PdfPCell cell = new PdfPCell(paragraph);
//        cell.setBorder(18);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        
        return cell;
    }
    
    
//    BaseFont getBaseFont() throws DocumentException, IOException{
//        String almaraiFontRegular = "/badrbillingsystem/resources/fonts/Almarai-Bold.ttf";
//        return BaseFont.createFont(almaraiFontRegular,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//    }
    
    void addPageHeader(Document document, long id, SalesInvoiceHeader header, ArrayList<SalesInvoiceDetails> details, Customer c, int pageNumber) {
        try {
            CompanyInfoRepo companyInfoRepo = new CompanyInfoRepo();
            CompanyInfo company = companyInfoRepo.findById(1);
            
            // font 
//            String almaraiFontRegular = "/badrbillingsystem/resources/fonts/Almarai-Regular.ttf";
            BaseFont base = BaseFont.createFont(almaraiFontRegular,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont base2 = BaseFont.createFont(almaraiFontBold,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font almarai = new Font(base, 9);
            Font almaraiBold = new Font(base2, 10);

//            FontProgram fontProgram = FontProgramFactory.createFont(almaraiFontRegular);
//            PdfFont almarai = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, true);
            
            String outputFileName = "./docs/" + "sales-invoice-"+id+".pdf";
//            Document document = new Document(PageSize.A4);
            
//            PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
                        
            document.open();  
            System.out.println("document opened");
            
            
            // company info , logo and qr code
            
            float newWidth[] = {175f,175f,220f};
            
            PdfPTable tbCompanyInfoAndLogos = new PdfPTable(newWidth);
//            PdfPTable tbCompanyInfoAndLogos = new PdfPTable(threeColWidth);
            
            
            // company info
            
            float testWidth[] = {300f};
            
//            PdfPTable companyInfoTable = new PdfPTable(testWidth);
            PdfPTable companyInfoTable = new PdfPTable(oneColWidth);
            PdfPCell companyInfo = getUnborderdCell("       " + company.getName() + "\n\n"
                    + "مواد بناء - سباكة - مواد كهرباء - نجارة" + "\n\n"
                    +"                  " +company.getPhone()+ "        " + "\n\n"
                    + "                 " +company.getBranch() +"\n\n", almarai);
            
            PdfPCell taxNumberCell = getborderdCell("الرقم الضريبي"+"  "+company.getTaxNumber(), almarai);
            
            PdfPCell taxNumberCell1 = getUnborderdCell("       " + company.getName() , almaraiBold);
            PdfPCell taxNumberCell2 = getUnborderdCell("مواد بناء - سباكة - مواد كهرباء - نجارة", almarai);
            PdfPCell taxNumberCell3 = getUnborderdCell("                  " +company.getPhone(), almarai);
            PdfPCell taxNumberCell4 = getUnborderdCell("                 " +company.getBranch() , almarai);
            
            
            companyInfoTable.addCell(taxNumberCell1);
            companyInfoTable.addCell(taxNumberCell2);
            companyInfoTable.addCell(taxNumberCell3);
            companyInfoTable.addCell(taxNumberCell4);
            
//            companyInfoTable.addCell(companyInfo);
            companyInfoTable.addCell(taxNumberCell);
            
            // logo
            Image logo = Image.getInstance(company.getLogo());
//            logo.setBorder(-1);
//            logo.setWidthPercentage(1);
            logo.setAbsolutePosition(200, 730);
//            logo.setCompressionLevel(2);
//            logo.setScaleToFitHeight(false);
            logo.setDpi(100, 100);
            
            document.add(logo);
//            System.out.println(logo.getBorder());
//            logo.scaleToFit(20,20);
//            logo.setPaddingTop(30);
            System.out.println(document.getPageSize());
            
            // qr code
            Image qrCode = Image.getInstance(company.getQrCode());
//            qrCode.setBorder(0);
            qrCode.setAbsolutePosition(70, 730);
            
            document.add(qrCode);
            
            // add to table
            tbCompanyInfoAndLogos.addCell(getUnborderdCell("", almarai));
            tbCompanyInfoAndLogos.addCell(getUnborderdCell("", almarai));
            tbCompanyInfoAndLogos.addCell(companyInfoTable);
            
            Paragraph p = new Paragraph("اختبار", almarai);
//            p.set
            
            document.add(tbCompanyInfoAndLogos);
            
            
            // line break
            Paragraph newLine = new Paragraph("\n");
            document.add(newLine);
            PdfPTable lineBreak = new PdfPTable(fullPageWidth);
            lineBreak.setSpacingBefore(2);
            PdfPCell lineBreakcCell = getborderdCell("", almarai);
            lineBreakcCell.setBorderWidth(1);
            lineBreak.addCell(lineBreakcCell);
//            document.add(lineBreak);
            
            
            // address 
            
            
            
            PdfPTable address = new PdfPTable(fullPageWidth);
            PdfPCell addressCell = getUnborderdCell(company.getAddress()+ " - " + "هاتف " + company.getPhone() + fiveTabs + pageNumber, almarai);
            addressCell.setHorizontalAlignment(1);
            addressCell.setBorder(3);
            
            address.addCell(addressCell);
            address.setSpacingBefore(2);

            document.add(address);
            
            
            // date and invoice id
            float d[] = {700f,700f,700f};
            PdfPTable date = new PdfPTable(d);
            
            PdfPCell dateCell = getborderdCell( "التاريخ"+ "        "+header.getDate() + fiveTabs + "Date", almarai);
            
            PdfPCell titleCell = getUnborderdCell("فاتورة ضريبية" + " " + "Tax Invoice", almaraiBold);
            titleCell.setPadding(5);
            
            PdfPCell invoiceIdCell = getborderdCell("رقم الفاتورة" + "  " + "           " + header.getId() + threeTabs +"Invoice", almarai);

            
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
            
            // products table 
            document.add(lineBreak);
            
            // table header 
            
            PdfPTable productsHeaderOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable productsHeaderTable = new PdfPTable(productsTableWidth);
            
            PdfPCell phTotalCell = getUnborderdCell("الاجمالي", almarai);
            PdfPCell phQuantityCell = getUnborderdCell("الكمية", almarai);
            PdfPCell phPriceWithTaxCell = getUnborderdCell("سعر مع الضريبة", almarai);
            PdfPCell phTaxCell = getUnborderdCell("ضريبة", almarai);
            PdfPCell phPriceCell = getUnborderdCell("سعر", almarai);
            PdfPCell phNameCell = getUnborderdCell("الصنف", almarai);
            phNameCell.setHorizontalAlignment(1);
            PdfPCell phNoCell = getUnborderdCell("رقم", almarai);
                    
            productsHeaderTable.addCell(phTotalCell);
            productsHeaderTable.addCell(phQuantityCell);
            productsHeaderTable.addCell(phPriceWithTaxCell);
            productsHeaderTable.addCell(phTaxCell);
            productsHeaderTable.addCell(phPriceCell);
            productsHeaderTable.addCell(phNameCell);
            productsHeaderTable.addCell(phNoCell);
            
            PdfPCell productTableHeaderOuterCell = new PdfPCell(productsHeaderTable);
            
           productsHeaderOuterTable.addCell(productsHeaderTable);
           productsHeaderOuterTable.setSpacingBefore(2);
           
           document.add(productsHeaderOuterTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void addPageFooter(Document document, long id, SalesInvoiceHeader header, ArrayList<SalesInvoiceDetails> details, Customer c, int pageNumber){
        
        try {
            // font 
            BaseFont base = BaseFont.createFont(almaraiFontRegular,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont base2 = BaseFont.createFont(almaraiFontBold,  BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font almarai = new Font(base, 9);
            Font almaraiBold = new Font(base2, 10);

            // total before tax
            PdfPTable totalBeforeTaxOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable totalBeforeTaxInnerTable = new PdfPTable(threeColWidth);
            
            
            double total = header.getTotal();
            double tax = header.getTax();
            double discount = header.getDiscount();
            double totalBeforeTaxAndDiscount = (total - tax) + discount;
            

            PdfPCell totalBeforeTaxCell1 = getUnborderdCell("Total With Out Tax With Out Discount", almarai);
            totalBeforeTaxCell1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell totalBeforeTaxCell2 = getUnborderdCell(decimalFormat.format(totalBeforeTaxAndDiscount), almarai);
            totalBeforeTaxCell2.setHorizontalAlignment(1);
            PdfPCell totalBeforeTaxCell3 = getUnborderdCell("الاجمالي قبل الخصم قبل الضريبة", almarai);
            
            totalBeforeTaxInnerTable.addCell(totalBeforeTaxCell1);
            totalBeforeTaxInnerTable.addCell(totalBeforeTaxCell2);
            totalBeforeTaxInnerTable.addCell(totalBeforeTaxCell3);
            
            totalBeforeTaxOuterTable.addCell(totalBeforeTaxInnerTable);
            
            if(details.size() < 10) {
                totalBeforeTaxOuterTable.setSpacingBefore(100);
            } else {
                totalBeforeTaxOuterTable.setSpacingBefore(20);
            }
            
            document.add(totalBeforeTaxOuterTable);
            
            // total discount 
            
            PdfPTable totalDiscountOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable totalDiscountInnerTable = new PdfPTable(threeColWidth);
            
            PdfPCell totalDiscountCell1 = getUnborderdCell("Total Discount", almarai);
            totalDiscountCell1.setHorizontalAlignment(1);
            PdfPCell totalDiscountCell2 = getUnborderdCell(decimalFormat.format(discount), almarai);
            totalDiscountCell2.setHorizontalAlignment(1);
            PdfPCell totalDiscountCell3 = getUnborderdCell("الخصم", almarai);
            totalDiscountCell3.setHorizontalAlignment(1);
            
            totalDiscountInnerTable.addCell(totalDiscountCell1);
            totalDiscountInnerTable.addCell(totalDiscountCell2);
            totalDiscountInnerTable.addCell(totalDiscountCell3);
            
            totalDiscountOuterTable.addCell(totalDiscountInnerTable);
            totalDiscountOuterTable.setSpacingBefore(2);
            
            document.add(totalDiscountOuterTable);
            
            // total tax 
            
            PdfPTable totalTaxOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable totalTaxInnerTable = new PdfPTable(threeColWidth);
            
            PdfPCell totalTaxCell1 = getUnborderdCell("Total Tax 15%", almarai);
            totalTaxCell1.setHorizontalAlignment(1);
            PdfPCell totalTaxCell2 = getUnborderdCell(decimalFormat.format(tax), almarai);
            totalTaxCell2.setHorizontalAlignment(1);
            PdfPCell totalTaxCell3 = getUnborderdCell("ضريبة القيمة المضافة 15%", almarai);
            totalTaxCell3.setHorizontalAlignment(1);
            
            totalTaxInnerTable.addCell(totalTaxCell1);
            totalTaxInnerTable.addCell(totalTaxCell2);
            totalTaxInnerTable.addCell(totalTaxCell3);
            
            totalTaxOuterTable.addCell(totalTaxInnerTable);
            totalTaxOuterTable.setSpacingBefore(2);
            
            document.add(totalTaxOuterTable);
            
            // total after tax and discount
            
            PdfPTable totalOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable totalInnerTable = new PdfPTable(threeColWidth);
            
            PdfPCell totalCell1 = getUnborderdCell("Total After Discount With Tax", almarai);
            totalCell1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell totalCell2 = getUnborderdCell(decimalFormat.format(total), almarai);
            totalCell2.setHorizontalAlignment(1);
            PdfPCell totalCell3 = getUnborderdCell("الاجمالي بعد الخصم شامل الضريبة", almarai);
            
            totalInnerTable.addCell(totalCell1);
            totalInnerTable.addCell(totalCell2);
            totalInnerTable.addCell(totalCell3);
            
            totalOuterTable.addCell(totalInnerTable);
            totalOuterTable.setSpacingBefore(2);
            
            document.add(totalOuterTable);
            
            // total in arabic
            
            PdfPTable totalInArabicTable = new PdfPTable(fullPageWidth);
            
            String totalInArabic = NumberConvertToArabicText.convert(new BigDecimal(total));
            PdfPCell totalInArabicCell = getborderdCell(totalInArabic, almarai);
            totalInArabicCell.setHorizontalAlignment(1);
            totalInArabicCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            totalInArabicCell.setPadding(5);
            
            totalInArabicTable.addCell(totalInArabicCell);
            totalInArabicTable.setSpacingBefore(2);
            
            document.add(totalInArabicTable);
            
            // paid and balance
            
            PdfPTable paidOuterTable = new PdfPTable(fullPageWidth);
            PdfPTable paidSpecialOuterTable = new PdfPTable(specialWidth);
            PdfPTable paidInnerTable1 = new PdfPTable(threeColSpecialWidth);
            PdfPTable paidInnerTable2 = new PdfPTable(threeColSpecialWidth);
            PdfPTable paidInnerSeperatorTable = new PdfPTable(speratorWidth);
            
            PdfPCell balanceCell1 = getUnborderdCell("Balance", almarai);
            balanceCell1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell balanceCell2 = getUnborderdCell("0.00", almarai);
            balanceCell2.setHorizontalAlignment(1);
            PdfPCell balanceCell3 = getUnborderdCell("المتبقي", almarai);
            
            paidInnerTable1.addCell(balanceCell1);
            paidInnerTable1.addCell(balanceCell2);
            paidInnerTable1.addCell(balanceCell3);
            
            
            
            PdfPCell totalPaidCell1 = getUnborderdCell("Total Paid", almarai);
            totalPaidCell1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            PdfPCell totalPaidCell2 = getUnborderdCell(decimalFormat.format(total), almarai);
            totalPaidCell2.setHorizontalAlignment(1);
            PdfPCell totalPaidCell3 = getUnborderdCell("المدفوع", almarai);
            
            paidInnerTable2.addCell(totalPaidCell1);
            paidInnerTable2.addCell(totalPaidCell2);
            paidInnerTable2.addCell(totalPaidCell3);
            
            PdfPCell speratorCell =  getUnborderdCell("", almarai);
            
            paidInnerSeperatorTable.addCell(speratorCell);
            
            paidOuterTable.addCell(paidInnerTable1);
            paidOuterTable.addCell(paidInnerSeperatorTable);
            paidOuterTable.addCell(paidInnerTable2);
            paidOuterTable.setSpacingBefore(2);
            
            paidSpecialOuterTable.addCell(paidInnerTable1);
            paidSpecialOuterTable.addCell(paidInnerSeperatorTable);
            paidSpecialOuterTable.addCell(paidInnerTable2);
            paidSpecialOuterTable.setSpacingBefore(2);
            
            
            
            document.add(paidSpecialOuterTable);
            
            
            // instructions
            
            PdfPTable instructionsTable = new PdfPTable(fullPageWidth);
            
            
            CompanyInfoRepo companyInfoRepo = new CompanyInfoRepo();
            CompanyInfo companyInfo = companyInfoRepo.findById(1);
            
            PdfPCell instructionsCell = getborderdCell(companyInfo.getInstructions(), almarai);
            instructionsCell.setPadding(3);
            
            
            instructionsTable.addCell(instructionsCell);
            instructionsTable.setSpacingBefore(2);
            
            document.add(instructionsTable);
            
            
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
