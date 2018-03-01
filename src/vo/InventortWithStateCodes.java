package vo;

import java.util.List;

public class InventortWithStateCodes {
	private List<Inventory> inventoryItems;
	private List<String> stateCodes;
	private String myGstStateCode ;
	public List<String> getStateCodes() {
		return stateCodes;
	}
	public void setStateCodes(List<String> stateCodes) {
		this.stateCodes = stateCodes;
	}
	public List<Inventory> getInventoryItems() {
		return inventoryItems;
	}
	public void setInventoryItems(List<Inventory> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}
	public String getMyGstStateCode() {
		return myGstStateCode;
	}
	public void setMyGstStateCode(String myGstStateCode) {
		this.myGstStateCode = myGstStateCode;
	}

}
