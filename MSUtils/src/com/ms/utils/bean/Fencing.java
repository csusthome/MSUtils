package com.ms.utils.bean;

public class Fencing {
	private Integer id;
	private String name;
	private double longitude;
	private double latitude;
	private double radius;
	private Entity entity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public boolean equals(Object obj) {
		Fencing fencing = (Fencing) obj;
		if (this.getId() == fencing.getId()) {
			return true;
		}
		return false;
	}
}
