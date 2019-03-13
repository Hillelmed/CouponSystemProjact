package com.hillel.couponsystem.exception;

import java.io.Serializable;

import com.hillel.couponsystem.enums.ErrorType;

public class CouponSystemExeption extends Exception implements Serializable{

	ErrorType errortype;
	String errorPrintFromDao;
	
	
	public CouponSystemExeption(){}
	
	public CouponSystemExeption(String message) {
		super(message);
	}
	public CouponSystemExeption(String message,Exception ex) {
		super(message,ex);
	}
	public CouponSystemExeption(ErrorType errortype) {
		this.errortype = errortype;
	}
	public CouponSystemExeption(ErrorType errortype,String errorPrintFromDao) {
		this.errorPrintFromDao = errorPrintFromDao;
		this.errortype = errortype;
	}
	private static final long serialVersionUID = 1L;

	public String getMessagefromErrorType() {
		return this.errortype.toString();
	}

	public ErrorType getErrortype() {
		return errortype;
	}

	public String getErrorPrintFromDao() {
		return errorPrintFromDao;
	}
	

	
}
