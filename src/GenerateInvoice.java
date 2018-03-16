import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.DocumentException;

import misc.Utils;
import pdf.InvoiceToPDF;
import vo.InvoiceDetails;
import vo.InvoiceItem;
import vo.Registration;

public class GenerateInvoice {

	public static void main(String[] args) {
		GenerateInvoice start = new GenerateInvoice();
		System.out.println("Please enter invoice Start line no & end line no e.g. 2-3 ");
		String lineNos = start.invoiceLineNo();
		String[] lineNoArray = lineNos.split("-");
		int lineNoFrom = Integer.parseInt(lineNoArray[0]);
		int lineNoTo = lineNoFrom;
		try{
			lineNoTo = Integer.parseInt(lineNoArray[1]);
		}catch(Exception e){
			
		}
		
		
		if (lineNoFrom< 2){
			lineNoFrom =2;
		}
		
		
		if (lineNoTo<lineNoFrom){
			lineNoTo =lineNoFrom ;
		}
		InvoiceToPDF pdf = new InvoiceToPDF();
		try {
			Registration registration = start.getRegistration();
			InvoiceDetails details = start.populateInvoice(lineNoFrom, lineNoTo);
			
			ByteArrayOutputStream bos = pdf.getPdfBytes(registration, details);
			FileOutputStream fos = new FileOutputStream("../invoices/Invoice"+details.getInvoiceNo()+".pdf");
			fos.write(bos.toByteArray());
			fos.flush();
			fos.close();
		} catch (DocumentException | IOException e) {
			
			e.printStackTrace();
		}
		

	}
	
	private InvoiceDetails populateInvoice(int lineNoFrom, int lineNoTo){
		InvoiceDetails aInvoice = new InvoiceDetails();
		List<String> invoiceItems = Utils.getFileContent("../data/invoice.csv", lineNoFrom, lineNoTo);
		int lineNo =0;
		for (String line:invoiceItems){
			if (lineNo ==0){//Meta data for invoice
				populateMetaData(line,aInvoice);
			}
			aInvoice.getMyCart().add(getInvoiceItem(line));
			lineNo++;
		}
		
		
		return aInvoice;
	}
	private InvoiceItem getInvoiceItem(String line){
		InvoiceItem item = new InvoiceItem();
		String[] lineExtract = line.split(",");
		item.setItem(lineExtract[11]);
		item.setHsn(lineExtract[12]);
		item.setQuantity(lineExtract[13]);
		
		try{
			item.setRate(Double.parseDouble(lineExtract[14].trim()));
		}catch(Exception e){
			
		}
		try{
			double qty = Integer.parseInt(item.getQuantity().trim());
			item.setTaxableValue(item.getRate()*qty);
		}catch(Exception e){
			
		}
		try{
			double cgstRate = Double.parseDouble(lineExtract[15].trim());
			item.setCgst(cgstRate);
			item.setCgstApplied(item.getTaxableValue()*cgstRate/100);
		}catch(Exception e){
			
		}
		
		try{
			double sgstRate = Double.parseDouble(lineExtract[16].trim());
			item.setSgst(sgstRate);
			item.setSgstApplied(item.getTaxableValue()*sgstRate/100);
		}catch(Exception e){
			
		}
		
		try{
			double igstRate = Double.parseDouble(lineExtract[17].trim());
			item.setIgst(igstRate);
			item.setIgstApplied(item.getTaxableValue()*igstRate/100);
		}catch(Exception e){
			
		}
		
		try{
			double cessRate = Double.parseDouble(lineExtract[18].trim());
			item.setCess(cessRate);
			item.setCessApplied(item.getTaxableValue()*cessRate/100);
		}catch(Exception e){
			
		}
		
		try{
			item.setRowTotal( item.getTaxableValue() +item.getCgstApplied()+item.getSgstApplied()+item.getIgstApplied()+item.getCessApplied());
		}catch(Exception e){
			
		}
		
		return item;
		
	}
	private void populateMetaData(String line, InvoiceDetails aInvoice){
		String[] lineExtract = line.split(",");
		aInvoice.setCustomerName(lineExtract[0]);
		aInvoice.setShippingAddress(lineExtract[1]);
		aInvoice.setShippingState(lineExtract[2]);
		aInvoice.setCustomerGSTIN(lineExtract[3]);
		aInvoice.setBillingAddress(lineExtract[4]);
		try{
			aInvoice.setInvoiceNo(Integer.parseInt(lineExtract[5]));
		}catch(Exception e){
			
		}
		try{
			aInvoice.setInvoiceDateFormatted(lineExtract[6]);
		}catch(Exception e){
			
		}
		aInvoice.setDispatchTime(lineExtract[7]);
		aInvoice.setModeOfTransport(lineExtract[8]);
		aInvoice.setVehicleNo(lineExtract[9]);
		aInvoice.setApproxDistanceKm(lineExtract[10]);
		
		
		
	}
	private String invoiceLineNo(){
		try{
			Scanner scanner = new Scanner(System.in);
			String lineNo = scanner.nextLine();
			return lineNo;
		}catch(Exception e){
			return "";
		}
		
	}
	
	private Registration getRegistration(){
		Registration registration = new Registration();
		registration.setGSTIN("06DEGPS4169B1ZG");
		registration.setbName("Mahadev Traders");
		registration.setState("Haryana (06)");
		registration.setPan("DEGPS4169B");
		registration.setEmail("singhanar0866@gmail.com");
		registration.setOwnerName("Anar Pawar");
		registration.setPhone("+91 9216124788 / 8360743792");
		registration.setAddress("Village Madanpur, Sector 26, Panchkula (Haryana)");
		registration.getTermsAndConditions().add("1. Goods once sold will not be returned.");
		registration.getTermsAndConditions().add( "2. All disputes, if any are subject to Panchkula Jurisdiction only.");
		registration.getTermsAndConditions().add(  "3. Interest will be charged @ 18% p.a. if payment is not made within 4 days.");
		registration.getTermsAndConditions().add(  "4. Please note cheque/DD should be made in favour of MAHADEV TRADERS");
		
		return registration;
	}

}
