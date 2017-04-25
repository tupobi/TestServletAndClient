package com.Jay.entity;

public class Manager {
	private String uname;
	private String upwd;

	public Manager(){
		
	}
	
	public Manager(String uname, String upwd) {
		super();
		this.uname = uname;
		this.upwd = upwd;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

}
