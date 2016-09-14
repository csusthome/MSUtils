package com.ms.utils.bean;

import java.util.Date;

public class Point {
	private Integer id;
	private double longitude;
	private double latitude;
	private Date date;
	private Pupillus pupillus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
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

	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;
		if ( point.getLatitude() == this.getLatitude()
				&& point.getLongitude() == this.getLongitude()) {
			return true;
		}
		return false;
	}

}
