package com.hillel.couponsystem.dao;
import java.util.ArrayList;

import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

public interface ICouponsDAO {
	boolean checkifCustomerHaveCoupon(long customerID,long couponID) throws CouponSystemExeption;
	boolean CheckifCompanyHaveCoupon(long comapnyID,long coupon) throws CouponSystemExeption;
	boolean CheckifCouponExists(long coupon) throws CouponSystemExeption;
	void createCoupon(Coupon coupon)throws CouponSystemExeption;
	void updateCoupon(Coupon coupon) throws CouponSystemExeption;
	Coupon readOneCoupon(long couponID) throws CouponSystemExeption;
	void deleteCoupon(long couponID)throws CouponSystemExeption;
	void addCouponPurchase(long customerID, long couponID) throws CouponSystemExeption;
	void deleteCouponPurchaseByCustomer(long customerID, long couponID) throws CouponSystemExeption;
	void deleteCouponPurchase(long couponID) throws CouponSystemExeption;
	void deleteCouponsByCompany(long compID) throws CouponSystemExeption;
	void deleteAllCouponPurchase(long couponID) throws CouponSystemExeption;
	void updateCouponPurchase(Coupon couponID) throws CouponSystemExeption;
	ArrayList<Coupon> readAllCoupons() throws CouponSystemExeption;
	ArrayList<Coupon> readCouponsByCompany(long CompanyID) throws CouponSystemExeption;
	ArrayList<Coupon> readCouponsByCustomer(long customerID) throws CouponSystemExeption;
	ArrayList<Coupon> readCompanyCouponByPrice(long companyID,double maxPrice) throws CouponSystemExeption;
	ArrayList<Coupon> readCompanyCouponByType(long companyID,CouponType type) throws CouponSystemExeption;
	ArrayList<Coupon> readCustomerCouponByPrice(long customerID, double price) throws CouponSystemExeption;
	ArrayList<Coupon> readCustomerCouponByType(long customerID,CouponType type) throws CouponSystemExeption;
	
}
