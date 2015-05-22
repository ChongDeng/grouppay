package com.chongdeng.entity;

public class Group {
	
	private String name;
	private String description;
	private String owner_name;
	private String treasurer_name;
	private String fund;
	
	public Group(String name, String description, String owner_name, String treasurer_name, String fund)
	{ 
		this.name = name;
		this.description = description;
		this.owner_name = owner_name;
		this.treasurer_name = treasurer_name;
		this.fund = fund;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOwnerName() {
		return owner_name;
	}
	public void setOwnerName(String owner_name) {
		this.owner_name = owner_name;
	}
	
	public String getTreasurerName() {
		return treasurer_name;
	}
	public void setTreasurerName(String treasurer_name) {
		this.treasurer_name = treasurer_name;
	}	
	
	public String getFund() {
		return this.fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}	


}