package com.hillel.couponsystem.enums;

public enum ErrorType {

	GENERAL_ERROR(600,"General error"),
	GENERAL_ERROR_BADLOGIN(600,"General bad login error"),
	GENERAL_ERROR_EMAILWEAK(600,"General email illegal error"),
	GENERAL_ERROR_PASSWORDWEAK(600,"General password illegal error"),
	GENERAL_ERROR_LOGGIN(600,"General error on data base loggin"),
	GENERAL_ERROR_DAO(600,"General error on data base"),
	GENERAL_ERROR_DAO_CREATE(600,"General error on data base on create"),
	GENERAL_ERROR_DAO_UPDATE(600,"General error on data base on update"),
	GENERAL_ERROR_DAO_READ(600,"General error on data base on read"),
	GENERAL_ERROR_DAO_DELETE(600,"General error on data base on delete"),
	GENERAL_ERROR_CPOOL(600,"General error on connection pool"),
	COMPANY_GENERAL(601,"COMPANY general error"),
	COMPANY_ALREADY_EXIST(601,"COMPANY already exist error"),
	COMPANY_MISS_DEATILS(601,"COMPANY miss deatils error"),
	COMPANY_EMAIL_EXIST(601,"COMPANY email already exist error"),
	COMPANY_NAME_EXIST(601,"COMPANY name already exist error"),
	COMPANY_ID_CANTCHANGE(601,"COMPANY id c'ant change error"),
	COMPANY_TRY_NOTCOUPON(601,"COMPANY try on coupons not yours"),
	COMPANY_NOT_EXIST(601,"COMPANY not exist"),
	CUSTOMER_GENERAL(602,"CUSTOMER general error"),
	CUSTOMER_ALREADY_EXIST(602,"CUSTOMER already exist error"),
	CUSTOMER_MISS_DEATILS(602,"CUSTOMER miss deatils error"),
	CUSTOMER_ALREADY_HAVETHISCOUPON(602,"CUSTOMER already have this coupon error"),
	CUSTOMER_EMAIL_EXIST(602,"CUSTOMER email already exist error"),
	CUSTOMER_NAME_EXIST(602,"CUSTOMER name already exist error"),
	CUSTOMER_ID_CANTCHANGE(602,"CUSTOMER id c'ant change error"),
	CUSTOMER_NOT_EXIST(602,"CUSTOMER not exist"),
	COUPON_GENERAL(603,"COUPON general error"),
	COUPON_ALREADY_EXIST(603,"COUPON already exist error"),
	COUPON_MISS_DETAILS(603,"COUPON miss details error"),
	COUPON_NAME_EXIST(603,"COUPON name already exist error"),
	COUPON_ID_ALREADY_EXIST(603,"COUPON id already exist error"),
	COUPON_ID_CANTCHANGE(603,"COUPON id c'ant change error"),
	COUPON_NOT_EXIST(603,"COUPON not exist"),
	COUPON_COUPONPURCHASE(603,"COUPON customer purchase error"),
	COUPON_EXPIREDE(603,"COUPON expired error"),
	;
	
	private int statuserror;
	private String message;
	
	private ErrorType(int statuserror,String message) {
		this.statuserror = statuserror;
		this.message = message;
	}

	public int getStatuserror() {
		return statuserror;
	}

	public String getMessage() {
		return message;
	}
	
	
}
