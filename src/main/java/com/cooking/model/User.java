package com.cooking.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -1636049308917874698L;
	
	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
