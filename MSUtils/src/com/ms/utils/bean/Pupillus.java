package com.ms.utils.bean;

public class Pupillus {
	private Integer id;
	private String meid;
	private String verification_code;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMeid() {
		return meid;
	}
	public void setMeid(String meid) {
		this.meid = meid;
	}
	public String getVerification_code() {
		return verification_code;
	}
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pupillus pupillus =(Pupillus)obj;
		if(this.getId() == pupillus.getId()){
			return true;
		}
		return false;
	}
}
