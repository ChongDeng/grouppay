package com.amaker.entity;

public class Event {

	private String name;
	private String description;
	private double cost;
	private String group_name;
	private String expense_strategy;
	private int pending_state;
	private String sponsor_name;

	
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
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
	public String getGroupName() {
		return group_name;
	}

	public void setGroupName(String group_name) {
		this.group_name = group_name;
	}
	
	public String getExpenseStrategy() {
		return expense_strategy;
	}

	public void setExpenseStrategy(String expense_strategy) {
		this.group_name = group_name;
	}
	
	public int getPendingState() {
		return pending_state;
	}

	public void setPendingState(int pending_state) {
		this.pending_state = pending_state;
	}
	
	public String getSponsorName() {
		return sponsor_name;
	}

	public void setSponsorName(String sponsor_name) {
		this.sponsor_name = sponsor_name;
	}
	
}
