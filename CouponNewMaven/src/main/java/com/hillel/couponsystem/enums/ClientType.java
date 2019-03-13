package com.hillel.couponsystem.enums;

public enum ClientType {
	Administrator,
	Company,
	Customer;
	
	public static ClientType getClientType(String type) {
		switch(type) {
			case "Administrator": return ClientType.Administrator;
			case "Company": return ClientType.Company;
			case "Customer": return ClientType.Customer;
			default: return null;
		}
	}
}
