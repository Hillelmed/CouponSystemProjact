package com.hillel.couponsystem.beans;

import java.util.Date;

import com.hillel.couponsystem.enums.IncomeType;

public class Income {

	private long id;
	private Date date;
	private String name;
	private IncomeType description;
	private double amount;
	
	
	public Income() {};
	
	public Income(long id, String name , IncomeType description,Date date, double amount) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
		this.description = description;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IncomeType getDescription() {
		return description;
	}

	public void setDescription(IncomeType description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Income [getId()=" + getId() + ", getDate()=" + getDate() + ", getName()=" + getName()
				+ ", getDescription()=" + getDescription() + ", getAmount()=" + getAmount() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Income other = (Income) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
}
