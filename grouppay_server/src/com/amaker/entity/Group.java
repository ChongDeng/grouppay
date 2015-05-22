package com.amaker.entity;

public class Group {
	
	private String name;
	private String description;
	private String owner_name;
	private String treasurer_name;
	private String member_list;
	private double fund;
	
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
	
	public String getMemberList() {
		return member_list;
	}
	public void setMemberList(String member_list) {
		this.member_list = member_list;
	}	
	
	public double getFund() {
		return this.fund;
	}
	public void setFund(double fund) {
		this.fund = fund;
	}	

}

