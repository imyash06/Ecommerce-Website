package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User_Data")
public class UserDtls {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String fullName;
	
	private String  email;
	
	private String address;
	
	private String mobile;
	
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDtls [id=" + id + ", fullName=" + fullName + ", email=" + email + ", address=" + address
				+ ", mobile=" + mobile + ", password=" + password + ", getId()=" + getId() + ", getFullName()="
				+ getFullName() + ", getEmail()=" + getEmail() + ", getAddress()=" + getAddress() + ", getMobile()="
				+ getMobile() + ", getPassword()=" + getPassword() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	public UserDtls(int id, String fullName, String email, String address, String mobile, String password) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.address = address;
		this.mobile = mobile;
		this.password = password;
	}

	public UserDtls() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}


