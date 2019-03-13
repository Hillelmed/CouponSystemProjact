package com.hillel.couponsystem.enums;

public enum IncomeType {
	
	CUSTOMER_BUY_COUPON("customer buy coupon"),
	COMPANY_CREATE_COUPON("company create a new coupon"),
	COMPANY_UPDATE_COUPON("company update an exist coupon");
	
	private String message;

	private IncomeType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
