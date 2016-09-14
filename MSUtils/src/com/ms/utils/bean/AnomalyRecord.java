package com.ms.utils.bean;

import java.util.Date;

public class AnomalyRecord {
	private Integer id;
	private Date date;
	private Pupillus pupillus;
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

	public Pupillus getPupillus() {
		return pupillus;
	}

	public void setPupillus(Pupillus pupillus) {
		this.pupillus = pupillus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean equals(Object obj){
		AnomalyRecord anomalyRecord = (AnomalyRecord)obj;
		if(this.getId() == anomalyRecord.getId()){
			return true;
		}
		return false;
	}
}
