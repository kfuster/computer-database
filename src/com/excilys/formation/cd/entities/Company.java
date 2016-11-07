package com.excilys.formation.cd.entities;

/**
 * Company entity
 * @author kfuster
 *
 */
public class Company {
	private int id;
	private String name;
	
	public Company(){
	}
	
	public Company(String pName){
		name = pName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	
}
