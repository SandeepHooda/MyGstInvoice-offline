package vo;

public class InvoiceItem {
	private String item;
	private String hsn;
	private String quantity;
	private double rate;
	private double taxableValue;
	private double cgstApplied;
	private double sgstApplied;
	private double igstApplied;
	private double cessApplied;
	private double cgst;
	private double sgst;
	private double igst;
	private double cess;
	private double rowTotal;
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getHsn() {
		if (null == hsn){
			hsn = "";
		}
		return hsn.trim();
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	public double getCgstApplied() {
		return cgstApplied;
	}
	public void setCgstApplied(double cgstApplied) {
		this.cgstApplied = cgstApplied;
	}
	public double getSgstApplied() {
		return sgstApplied;
	}
	public void setSgstApplied(double sgstApplied) {
		this.sgstApplied = sgstApplied;
	}
	public double getIgstApplied() {
		return igstApplied;
	}
	public void setIgstApplied(double igstApplied) {
		this.igstApplied = igstApplied;
	}
	public double getCessApplied() {
		return cessApplied;
	}
	public void setCessApplied(double cessApplied) {
		this.cessApplied = cessApplied;
	}
	public double getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(double rowTotal) {
		this.rowTotal = rowTotal;
	}
	public double getCgst() {
		return cgst;
	}
	public void setCgst(double cgst) {
		this.cgst = cgst;
	}
	public double getSgst() {
		return sgst;
	}
	public void setSgst(double sgst) {
		this.sgst = sgst;
	}
	public double getIgst() {
		return igst;
	}
	public void setIgst(double igst) {
		this.igst = igst;
	}
	public double getCess() {
		return cess;
	}
	public void setCess(double cess) {
		this.cess = cess;
	}
	public double getTaxableValue() {
		return taxableValue;
	}
	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	

}
