package twkg.entity;

import java.sql.Date;

public class Cookie {
	private int cookieId;
	private int userId;
	private String cookieValue = null;
	private Date cookieTime = null;
	private boolean isAutoLogin;
	public int getCookieId() {
		return cookieId;
	}
	public void setCookieId(int cookieId) {
		this.cookieId = cookieId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
	public Date getCookieTime() {
		return cookieTime;
	}
	public void setCookieTime(Date cookieTime) {
		this.cookieTime = cookieTime;
	}
	public boolean isAutoLogin() {
		return isAutoLogin;
	}
	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
}
