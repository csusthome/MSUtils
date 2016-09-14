package com.ms.utils.bean;

public class Entity {
	private Integer id;
	private User user;
	private Pupillus pupillus;
	private String alias;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pupillus getPupillus() {
		return pupillus;
	}

	public void setPupillus(Pupillus pupillus) {
		this.pupillus = pupillus;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public boolean equals(Object obj) {
		Entity entity = (Entity)obj;
		if(this.getId() == entity.getId()){
			return true;
		}
		return false;
	}
}
