package vo;

import java.io.Serializable;

public class Inventory  extends Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hsn;
	private String inventoryDesc;
	private String modelNo;
	private int purchasePriceWithoutTax;
	private int salesPriceOnlineWithoutTax;
	private int salesPriceRegularWithoutTax;
	public String getHsn() {
		return hsn;
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public int getPurchasePriceWithoutTax() {
		return purchasePriceWithoutTax;
	}
	public void setPurchasePriceWithoutTax(int purchasePriceWithoutTax) {
		this.purchasePriceWithoutTax = purchasePriceWithoutTax;
	}
	
	public int getSalesPriceRegularWithoutTax() {
		return salesPriceRegularWithoutTax;
	}
	public void setSalesPriceRegularWithoutTax(int salesPriceRegularWithoutTax) {
		this.salesPriceRegularWithoutTax = salesPriceRegularWithoutTax;
	}
	public String getInventoryDesc() {
		return inventoryDesc;
	}
	public void setInventoryDesc(String inventoryDesc) {
		this.inventoryDesc = inventoryDesc;
	}
	public int getSalesPriceOnlineWithoutTax() {
		return salesPriceOnlineWithoutTax;
	}
	public void setSalesPriceOnlineWithoutTax(int salesPriceOnlineWithoutTax) {
		this.salesPriceOnlineWithoutTax = salesPriceOnlineWithoutTax;
	}

}
