package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Registration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id; //ID for mango db
	private String regID =""; //Unique ID to identify person
	private String userID ="";//userID associated with a GST ID. with one to one mapping.
	private String pwd;//Password for the above user id 
	private String mdbInvoiceStore;//DB  name where invoice are found for this user 
	private String GSTIN ="";// GST ID
	private String bName ="";//Business name
	private String state ="";//indian state code
	private String pan ="";
	private int invoiceStart;// Start no of invoice
	private String phone ="";
	private String email ="";
	private String logo ="logo.png";
	private String ownerName ="";
	private String address ="";
	private List<String> termsAndConditions = new ArrayList<String>();
	
	private List<Product> products;
	private List<Inventory> inventory;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getRegID() {
		return regID;
	}
	public void setRegID(String regID) {
		this.regID = regID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getGSTIN() {
		return GSTIN;
	}
	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public int getInvoiceStart() {
		return invoiceStart;
	}
	public void setInvoiceStart(int invoiceStart) {
		this.invoiceStart = invoiceStart;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<Inventory> getInventory() {
		return inventory;
	}
	public void setInventory(List<Inventory> inventory) {
		this.inventory = inventory;
	}
	public String getMdbInvoiceStore() {
		return mdbInvoiceStore;
	}
	public void setMdbInvoiceStore(String mdbInvoiceStore) {
		this.mdbInvoiceStore = mdbInvoiceStore;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getTermsAndConditions() {
		if (null == termsAndConditions){
			termsAndConditions = new ArrayList<>();
		}
		return termsAndConditions;
	}
	public void setTermsAndConditions(List<String> termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

}
