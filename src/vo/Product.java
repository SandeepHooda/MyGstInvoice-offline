package vo;

import java.io.Serializable;

public class Product  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productHsn;
	private int cgst;// center gst rate
	private int sgst;// State Gst rate
	private int igst;//integrated gst rate
	private int cess;//addition cess
	private String productDesc;//Product description
	
	public int getCgst() {
		return cgst;
	}
	public void setCgst(int cgst) {
		this.cgst = cgst;
	}
	public int getSgst() {
		return sgst;
	}
	public void setSgst(int sgst) {
		this.sgst = sgst;
	}
	public int getIgst() {
		return igst;
	}
	public void setIgst(int igst) {
		this.igst = igst;
	}
	public int getCess() {
		return cess;
	}
	public void setCess(int cess) {
		this.cess = cess;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductHsn() {
		return productHsn;
	}
	public void setProductHsn(String productHsn) {
		this.productHsn = productHsn;
	}
	
	
	

}
