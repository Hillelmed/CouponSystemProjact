package com.hillel.couponsystem.enums;

public enum CouponType {
	
	RESTAURANT,
	ELECTRICITY,
	FOOD,
	HEALTH,
	SPORTS,
	CAMPING,
	TRAVELLING;
	
public static CouponType getCategorie(String type) {
	
	switch(type) {
		case "FOOD": return CouponType.FOOD;
		case "RESTAURANT": return CouponType.RESTAURANT;
		case "ELECTRICITY": return CouponType.ELECTRICITY;
		case "HEALTH": return CouponType.HEALTH;
		case "SPORTS": return CouponType.SPORTS;
		case "CAMPING": return CouponType.CAMPING;
		case "TRAVELLING": return CouponType.TRAVELLING;
		default: return null;
		
	}

}

}