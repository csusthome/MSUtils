package com.ms.utils.bean;

public class WhiteNum {
	private Integer id;
	private String phone_number; 
	private String note;
	private Pupillus pupillus;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Pupillus getPupillus() {
		return pupillus;
	}
	public void setPupillus(Pupillus pupillus) {
		this.pupillus = pupillus;
	}
	
	@Override
	public boolean equals(Object obj) {
		WhiteNum whiteNum =(WhiteNum)obj;
		if(this.getId() == whiteNum.getId()){
			return true;
		}
		return false;
	}
}
