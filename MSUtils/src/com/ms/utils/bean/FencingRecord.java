package com.ms.utils.bean;

import java.util.Date;

public class FencingRecord {
	private Integer id;
	private Date date;
	private Fencing fencing;
	private String message;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Fencing getFencing() {
		return fencing;
	}
	public void setFencing(Fencing fencing) {
		this.fencing = fencing;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public boolean equals(Object obj) {
		FencingRecord fencingRecord =(FencingRecord)obj;
		if(this.getId() == fencingRecord.getId()){
			return true;
		}
		return false;
	}
	
}
