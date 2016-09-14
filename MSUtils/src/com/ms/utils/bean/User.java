package com.ms.utils.bean;

public class User {
	private Integer id;
	private String phone_number;
	private String pwd;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public boolean equals(Object obj) {
		User user =(User)obj;
		if(this.getId() == user.getId()){
			return true;
		}
		return false;
	}
}
