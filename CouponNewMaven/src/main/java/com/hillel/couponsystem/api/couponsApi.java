package com.hillel.couponsystem.api;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hillel.couponsystem.entitiy.CouponEntity;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.logic.CouponController;
import com.hillel.couponsystem.util.Utilextractor;

@RestController
@RequestMapping("/coupons")
public class couponsApi {

	@Autowired
	private CouponController couponcontroller;

	@RequestMapping(method = RequestMethod.POST)
	public void createCoupon(HttpServletRequest req,@RequestBody CouponEntity coupon) throws CouponSystemExeption {
		couponcontroller.createCoupon(coupon);
	}

	@RequestMapping(value = "/{couponID}" ,method = RequestMethod.GET)
	public CouponEntity readoneCoupon(@PathVariable("couponID") long couponID,HttpServletRequest req) throws CouponSystemExeption {
		return couponcontroller.readCoupon(couponID);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateCoupon(HttpServletRequest req,@RequestBody CouponEntity coupon) throws CouponSystemExeption {
		long company = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		couponcontroller.updateCoupon(company, coupon);
	}

	@RequestMapping(value = "/{couponID}" ,method = RequestMethod.DELETE)
	public void deleteCoupon(HttpServletRequest req,@PathVariable("couponID") long coupon) throws CouponSystemExeption {
		long company = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		couponcontroller.deleteCoupon(company, coupon);
	}

	@RequestMapping(value = "/readall" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readAllCoupons(HttpServletRequest req) throws CouponSystemExeption {
		return couponcontroller.readAllCoupons();
	}

	@RequestMapping(value = "/getpurchases/{idcoupon}" ,method = RequestMethod.POST)
	public void CloseOnpurchaseCoupon(HttpServletRequest req,@PathVariable("idcoupon") long couponid) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		couponcontroller.CloseOnCouponpurchasesCoupon(customerID, couponid);
	}
	@RequestMapping(value = "/purchases/{idcoupon}" ,method = RequestMethod.POST)
	public void purchaseCoupon(HttpServletRequest req,@PathVariable("idcoupon") long couponid) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		couponcontroller.purchasesCoupon(customerID, couponid);
	}
    /*
     * read all company coupons By Method type,maxprice,all
     */
	@RequestMapping(value = "/readcompanycoupons" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readallCoupByComp(HttpServletRequest req) throws CouponSystemExeption {
		long CompanyID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCompanyCoupons(CompanyID);
	}
	@RequestMapping(value = "/readcompanycouponsbytype" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readCompanyCouponsByType(HttpServletRequest req,@RequestParam("type") CouponType type) throws CouponSystemExeption {
		long CompanyID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCompanyCouponsByType(CompanyID, type);
	}
	@RequestMapping(value = "/readcompanycouponsbyprice" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readCompanyCouponsByType(HttpServletRequest req,@RequestParam("price")double Maxprice) throws CouponSystemExeption {
		long CompanyID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCompanyCouponsByPrice(CompanyID, Maxprice);
	}
    /*
     * read all customer coupons By Method type,maxprice,all
     */
	@RequestMapping(value = "/readcustomercoupons" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readallCoupByCust(HttpServletRequest req) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCustomerCoupons(customerID);
	}
	@RequestMapping(value = "/readcustomercouponsbytype" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readCustomerCouponsByType(HttpServletRequest req,@RequestParam("type") CouponType type) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCustomerCouponsByType(customerID, type);
	}
	@RequestMapping(value = "/readcustomercouponsbyprice" ,method = RequestMethod.GET)
	public Collection<CouponEntity> readCustomerCouponsByPrice(HttpServletRequest req,@RequestParam("price")double Maxprice) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return couponcontroller.readCustomerCouponsByPrice(customerID, Maxprice);
	}
	

}
