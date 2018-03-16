package pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.text.DecimalFormat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import util.EnglishNumberToWords;
import vo.InvoiceDetails;
import vo.InvoiceItem;
import vo.Registration;
public class InvoiceToPDF {

	private BaseFont bfBold;
	 private BaseFont bf;
	 private int pageNumber = 0;
     private int penPosY = 0;
     private int lineHeight = 15;
     private int itemWidth = 100;
     private int hsnWidth = 60;
     private int qtyWidth = 50;
     private int priceWidth = 55;
     private int taxableWidth = 55;
     private int taxWidth = 45;
     private int cessWidth = 35;
     private  DecimalFormat df = new DecimalFormat("0.00");
     private static final Logger log = Logger.getLogger(InvoiceToPDF.class.getName());
	public ByteArrayOutputStream getPdfBytes(Registration registration, InvoiceDetails invoiceDetails) throws DocumentException, IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 Document doc = new Document();
		  PdfWriter docWriter = null;
		  initializeFonts();

		  try {
		   
		   docWriter = PdfWriter.getInstance(doc , bos);
		   doc.addAuthor(registration.getbName());
		   doc.addCreationDate();
		   doc.addProducer();
		   doc.addCreator(registration.getbName());
		   doc.addTitle(""+invoiceDetails.getInvoiceNo());
		   doc.setPageSize(PageSize.LETTER);

		   doc.open();
		   PdfContentByte cb = docWriter.getDirectContent();
		   
		   boolean beginPage = true;
		   penPosY = 0;
		   List<InvoiceItem> allItems = new ArrayList<InvoiceItem>();
		   allItems.addAll(invoiceDetails.getMyCart());
		   allItems.addAll(invoiceDetails.getMyCartManual());
		   int index = 0;
		   //Test invoice with multiple items
		   //allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));allItems.add(allItems.get(0));
		   for(InvoiceItem aItem: allItems ){
		    if(beginPage){
		     beginPage = false;
		     generateLayout(doc, cb, registration.getLogo()); 
		     generateHeader(doc, cb,invoiceDetails, registration);
		     penPosY = 570; 
		    }
		    int linesConsumed = generateDetail(doc, cb, ++index, penPosY, aItem);
		    
		    penPosY -= ++linesConsumed *lineHeight;
		    if(penPosY < 250){
		     printPageNumber(cb, false);
		     doc.newPage();
		     beginPage = true;
		    }
		   }
		   printTermsAndConditions(cb,  registration.getTermsAndConditions());
		   printPageNumber(cb, true);
		   printTotal(cb, invoiceDetails);
		   

		  }
		  catch (DocumentException dex)
		  {
		   dex.printStackTrace();
		   sendEmail(dex);
		  }
		  catch (Exception ex)
		  {
		   ex.printStackTrace();
		   sendEmail(ex);
		  }
		  finally
		  {
		   if (doc != null)
		   {
		    doc.close();
		   }
		   if (docWriter != null)
		   {
		    docWriter.close();
		   }
		  }
		  return bos;
	}

		 private void generateLayout(Document doc, PdfContentByte cb, String image)  {

		  try {

		   cb.setLineWidth(1f);

		

		   // Invoice Header box Text Headings 
		   createHeadings(cb,422,740,"Invoice No:");
		   createHeadings(cb,422,730,"Invoice Date:");
		   createHeadings(cb,422,720,"Mode of Transport:");
		   createHeadings(cb,422,710,"Vehicle No:");
		   createHeadings(cb,422,700,"Approx distance (KM):");
		   createHeadings(cb,422,690,"Dispatch time:");
		   
		   //createHeadings(cb,422,703,"");

		   // Invoice Detail box layout 
		   int verticalLineStart = 600;
		   int verticalLineEnd = 150;
		   int boxWidth = 550;
		   int boxHeight = 450;
		   cb.rectangle(20,verticalLineEnd,boxWidth,boxHeight);
		   cb.moveTo(20,585);
		   cb.lineTo(570,585);
		   
		   
		   
		   
		   
		   int marginLeft = 1;
		   // Invoice Detail box Text Headings 
		   int headingY = 590;
		   int headingX = 22;
		   createHeadings(cb,headingX,headingY,"Item");
		   headingX += itemWidth;
		   createHeadings(cb,headingX,headingY,"HSN");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += hsnWidth;
		   createHeadings(cb,headingX,headingY,"Qty (Bag)");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += qtyWidth;
		   createHeadings(cb,headingX,headingY,"Price");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += priceWidth;
		   createHeadings(cb,headingX,headingY,"Taxable value");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += taxableWidth;
		   createHeadings(cb,headingX,headingY,"CGST");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += taxWidth;
		   createHeadings(cb,headingX,headingY,"SGST");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += taxWidth;
		   createHeadings(cb,headingX,headingY,"IGST");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += taxWidth;
		   createHeadings(cb,headingX,headingY,"CESS");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);
		   headingX += cessWidth;
		   createHeadings(cb,headingX,headingY,"Total");
		   cb.moveTo(headingX-marginLeft,verticalLineEnd);
		   cb.lineTo(headingX-marginLeft,verticalLineStart);

		   cb.stroke();
		   //add the images
		   ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		   Image companyLogo = null;
		   try{
			   String path = classLoader.getResource("images/"+image).getPath();
			   companyLogo = Image.getInstance(path);
			   
		   }catch(Exception e){
			   companyLogo =Image.getInstance("images/"+image);
		   }
		   
		  
		   companyLogo.setAbsolutePosition(20,675);
		   companyLogo.scalePercent(25);
		   doc.add(companyLogo);

		  }

		  catch (DocumentException dex){
		   dex.printStackTrace();
		   sendEmail(dex);
		  }
		  catch (Exception ex){
		   ex.printStackTrace();
		   sendEmail(ex);
		  }

		 }
		 private void printTotal(PdfContentByte cb, InvoiceDetails invoiceDetails){
			 
			 double totalTaxable = 0;
			 double totalIgst = 0;
			 double totalCgst =0;
			 double totalSgst = 0;
			 double totalCess = 0;
			 double totalInvoice = 0;
			 for (InvoiceItem item: invoiceDetails.getMyCart()){
				 if (item.getRowTotal() > 0){
					 totalTaxable += item.getTaxableValue();
					 totalIgst += item.getIgstApplied();
					 totalSgst += item.getSgstApplied();
					 totalCgst += item.getCgstApplied();
					 totalCess += item.getCessApplied();
				 }
				 
			 }
			 for (InvoiceItem item: invoiceDetails.getMyCartManual()){
				 if (item.getRowTotal() > 0){
					 totalTaxable += item.getTaxableValue();
					 totalIgst += item.getIgstApplied();
					 totalSgst += item.getSgstApplied();
					 totalCgst += item.getCgstApplied();
					 totalCess += item.getCessApplied();
				 }
			 }
			 
			 totalInvoice = totalTaxable +totalIgst+totalSgst+totalCgst+totalCess;
			 
			 //Draw Total line 
			 cb.moveTo(20,170);
			 cb.lineTo(570,170);
			 CMYKColor black = new CMYKColor(0.f, 0.f, 0.f, 0.34f);
			 cb.setColorStroke(black);
			 cb.stroke();
			 
			 int totalPosY = 155;
			 int totalPosX = 25;
			 createHeadings(cb,totalPosX,totalPosY,"Total");
			 totalPosX = 300;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalTaxable));
			 totalPosX += taxableWidth;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalCgst));
			 totalPosX += taxWidth;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalSgst));
			 totalPosX += taxWidth;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalIgst));
			 totalPosX += taxWidth;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalCess));
			 totalPosX += cessWidth;
			 createHeadings(cb,totalPosX,totalPosY,df.format(totalInvoice));
			 String totalValue = df.format(totalInvoice);
			 String totalValueWhole = totalValue.substring(0,totalValue.indexOf("."));
			 String totalValuePaisa = totalValue.substring((totalValue.indexOf(".")+1));
			 totalValueWhole = totalValueWhole.replaceAll(",", "");
			 String amountInWords = EnglishNumberToWords.convert(Long.parseLong(totalValueWhole))+" Rupees and "+EnglishNumberToWords.convert(Long.parseLong(totalValuePaisa))+" Paisa";
			 amountInWords = amountInWords.replaceAll(", +Rupees", " Rupees");
			 System.out.println(amountInWords);
			createHeadings(cb,25,135,"Amount in words: "+amountInWords,10);
		 }
		 private void generateHeader(Document doc, PdfContentByte cb, InvoiceDetails invoiceDetails, Registration registration)  {

		  try {
		   int registerationDetailsX = 130;
		   createHeadings(cb,registerationDetailsX,760,registration.getbName(),10);
		   createHeadings(cb,registerationDetailsX,750,registration.getAddress(),10);
		   createHeadings(cb,registerationDetailsX,740,"GSTIN: "+registration.getGSTIN(),10);
		   createHeadings(cb,registerationDetailsX,730,"State: "+registration.getState(),10);
		   createHeadings(cb,registerationDetailsX,720,"PAN: "+registration.getPan(),10);
		   createHeadings(cb,registerationDetailsX,710,"Phone: "+registration.getPhone(),10);
		   createHeadings(cb,registerationDetailsX,700,"Email: "+registration.getEmail(),10);
		   
		   
		  
		   createHeadings(cb,510,740 ,""+invoiceDetails.getInvoiceNo());
		   createHeadings(cb,510,730,invoiceDetails.getInvoiceDateFormatted());
		   createHeadings(cb,510,720,invoiceDetails.getModeOfTransport());
		   createHeadings(cb,510,710,invoiceDetails.getVehicleNo());
		   createHeadings(cb,510,700,invoiceDetails.getApproxDistanceKm());
		   createHeadings(cb,510,690,invoiceDetails.getDispatchTime());
		   CMYKColor magentaColor = new CMYKColor(0.f, 1.f, 0.f, 0.f);
		   cb.setColorStroke(magentaColor);
		   cb.setLineWidth(1f);
		  
		   // Invoice Header box layout
		 
		   cb.moveTo(20,670);
		   cb.lineTo(570,670);
		   cb.stroke();
		   
		   createHeadings(cb,280,680,"Tax Invoice");
		   
		   int customerDetailsX = 20;
		   createHeadings(cb,customerDetailsX,660,"Customer Name: "+invoiceDetails.getCustomerName());
		   createHeadings(cb,customerDetailsX,650,"Shipping Address: "+invoiceDetails.getShippingAddress());
		   createHeadings(cb,customerDetailsX,640,"State: "+invoiceDetails.getShippingState());
		   createHeadings(cb,customerDetailsX,630,"GSTIN No: "+invoiceDetails.getCustomerGSTIN());
		   createHeadings(cb,customerDetailsX,620,"Billing Address: "+invoiceDetails.getBillingAddress());
		   
		   cb.moveTo(20,610);
		   cb.lineTo(570,610);
		   cb.stroke();

		  }

		  catch (Exception ex){
		   ex.printStackTrace();
		   sendEmail(ex);
		  }

		 }
		 
		 private int generateDetail(Document doc, PdfContentByte cb, int index, int y, InvoiceItem aItem)  {
		 
		  int noOfLines = 0;
		  if (aItem.getRowTotal() ==0){
			  return 1;
		  }
		  try {
		  int penPosX = 22;
		  String[] itemDescParts = (String.valueOf(index) +". "+ aItem.getItem()).split(" ");
		  String lineContent = "";
		  
		  for (String part: itemDescParts){
			  if ((lineContent+" "+part).length() < 30 ){
				  lineContent +=" "+part;
			  }else {
				 
				  createContent(cb,penPosX,(y- noOfLines++*lineHeight),lineContent,PdfContentByte.ALIGN_LEFT);
				  lineContent = part;
			  }
			  
		  }
		  createContent(cb,penPosX,(y- noOfLines++*lineHeight),lineContent,PdfContentByte.ALIGN_LEFT);
		  int marginLeft = 2;
		  penPosX += itemWidth;
		  createContent(cb,penPosX+marginLeft,y, aItem.getHsn() ,PdfContentByte.ALIGN_LEFT);
		  penPosX += hsnWidth;
		  createContent(cb,penPosX+marginLeft,y, aItem.getQuantity() ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += qtyWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getRate()) ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += priceWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getTaxableValue()) ,PdfContentByte.ALIGN_LEFT);
		 
		  penPosX += taxableWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getCgstApplied()) ,PdfContentByte.ALIGN_LEFT);
		  createContent(cb,penPosX+marginLeft,y-lineHeight, "@ "+aItem.getCgst() +"%" ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += taxWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getSgstApplied()) ,PdfContentByte.ALIGN_LEFT);
		  createContent(cb,penPosX+marginLeft,y-lineHeight, "@ "+aItem.getSgst() +"%" ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += taxWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getIgstApplied()) ,PdfContentByte.ALIGN_LEFT);
		  createContent(cb,penPosX+marginLeft,y-lineHeight, "@ "+aItem.getIgst() +"%" ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += taxWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getCessApplied()) ,PdfContentByte.ALIGN_LEFT);
		  createContent(cb,penPosX+marginLeft,y-lineHeight, "@ "+aItem.getCess() +"%" ,PdfContentByte.ALIGN_LEFT);
		  
		  penPosX += cessWidth;
		  createContent(cb,penPosX+marginLeft,y, df.format(aItem.getRowTotal()) ,PdfContentByte.ALIGN_LEFT);
		  
		   
		  }

		  catch (Exception ex){
		   ex.printStackTrace();
		   sendEmail(ex);
		  }
		  if (noOfLines == 1){
			  noOfLines++;
		  }
		  return noOfLines;
		 }

		 private void createHeadings(PdfContentByte cb, float x, float y, String text){


			 createHeadings( cb,  x,  y,  text, 8);

		 }
		 
		 private void createHeadings(PdfContentByte cb, float x, float y, String text, int fontSize){


			  cb.beginText();
			  cb.setFontAndSize(bfBold, fontSize);
			  cb.setTextMatrix(x,y);
			  cb.showText(text.trim());
			  cb.endText(); 

			 }
		 
		 private void  printTermsAndConditions(PdfContentByte cb,  List<String> termsAndConditions){
			 cb.beginText();
			 cb.setFontAndSize(bfBold, 8);
			 int tcPosition = 110;
			 cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "E.& O.E", 20 , tcPosition, 0);
			 tcPosition -= 10;
			 for(String tcText : termsAndConditions){
				 cb.showTextAligned(PdfContentByte.ALIGN_LEFT, tcText, 20 , tcPosition, 0);
				 tcPosition -= 10;
			 }
			 
			  
			  cb.endText();
			 
		 }
		 private void printPageNumber(PdfContentByte cb, boolean isLastPage){


		  cb.beginText();
		  cb.setFontAndSize(bfBold, 8);
		  String nextPageMsg = " Cont...";
		  if (isLastPage){
			  nextPageMsg = " End.";
		  }
		  cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1)+nextPageMsg, 570 , 25, 0);
		  cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Authorised Signature", 570 , 45, 0);
		  cb.endText(); 
		  
		  pageNumber++;
		  
		 }
		 
		 private void createContent(PdfContentByte cb, float x, float y, String text, int align){


		  cb.beginText();
		  cb.setFontAndSize(bf, 8);
		  cb.showTextAligned(align, text.trim(), x , y, 0);
		  cb.endText(); 

		 }

		 private void initializeFonts(){


		  try {
		   bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		   bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

		  } catch (DocumentException e) {
		   e.printStackTrace();
		   sendEmail(e);
		  } catch (IOException e) {
		   e.printStackTrace();
		   sendEmail(e);
		  }


		 }
		 
		 private void sendEmail(Exception e){
			 try{
				 log.warning("Some exception occured while generating PDF of invoice."+e.getMessage());
				//new MailService().sendMultipartMail("sonu.hooda@gmail.com", null,"Sandeep",null, "Error generated in invoice system","Some exception occured while generating PDF of invoice.<br/>"+ e.getMessage());
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
		 }

		}