package com.dac.dto;

public class LoginCredentials {
	
	private String userName;

	private String password;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public LoginCredentials() {
	}

	public LoginCredentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
}
