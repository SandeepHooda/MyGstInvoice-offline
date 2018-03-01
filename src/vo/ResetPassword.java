package vo;

import java.io.Serializable;

public class ResetPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String uuid;
	private String email;
	private long generateTime;
	private int validTillMinutes = 10;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
		this._id = uuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(long generateTime) {
		this.generateTime = generateTime;
	}
	public int getValidTillMinutes() {
		return validTillMinutes;
	}
	public void setValidTillMinutes(int validTillMinutes) {
		this.validTillMinutes = validTillMinutes;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

}
