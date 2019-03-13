package com.hillel.couponsystem.logic;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hillel.couponsystem.dao.CouponsDAOJPA;
import com.hillel.couponsystem.entitiy.CouponEntity;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

/**
 * Coupon facade Control all the System
 * 
 * @author HillelMed
 */
@Controller
public class CouponController {
	
	@Autowired
	private CouponsDAOJPA couponsDAO;
//	@Autowired
//	private ICouponsDAO couponsDAO;

	/**
	 * Create a New Coupon and if pass validate Create him on DAO
	 * 
	 * @return Void
	 */
	public void createCoupon(CouponEntity coupon) throws CouponSystemExeption {
		ValidateCreateCoupon(coupon);
		couponsDAO.createCoupon(coupon);
	}

	/**
	 * get One Coupon on System By long
	 * 
	 * @throws CouponSystemExeption
	 */
	public CouponEntity readCoupon(long Coupid) throws CouponSystemExeption {
		return couponsDAO.readOneCoupon(Coupid);
	}

	/**
	 * update a specific coupon's details
	 * 
	 * @param coupon - this coupon will be updated with new information
	 * @throws CouponSystemExeption
	 */
	public void updateCoupon(long company, CouponEntity coupon) throws CouponSystemExeption {
		    ValidateifCouponUpdatetoCompany(company, coupon);
			couponsDAO.updateCoupon(coupon);
	}

	/**
	 * delete a specific coupon
	 * 
	 * @param couponID - this coupon will be deleted
	 * @throws Exception
	 */
	public void deleteCoupon(long company, long couponID) throws CouponSystemExeption {
		    ValidateifCouponBelongsCompany(company, couponID);
			couponsDAO.deleteCoupon(couponID);
		}
	/**
	 * delete a specific coupon
	 * 
	 * @param couponID - this coupon will be deleted
	 * @throws Exception
	 */
	public void deleteCouponsByComapny(long company) throws CouponSystemExeption {
			couponsDAO.deleteCouponsByCompany(company);
		}

	/**
	 * purchaseCoupon By Customer and id
	 * 
	 * @throws Exception
	 */
	public void CloseOnCouponpurchasesCoupon(long customerID, long coupon) throws CouponSystemExeption {
		    ValidateifcanPurchasedCoupon(customerID, coupon);
		    CouponEntity couponnow = couponsDAO.readOneCoupon(coupon);
			int temp = couponnow.getAmount();
			temp--;
			couponnow.setAmount(temp);
			couponsDAO.updateCouponPurchase(couponnow);
	}
	/**
	 * purchaseCoupon By Customer and id
	 * 
	 * @throws CouponSystemExeption
	 */
	public void purchasesCoupon(long customerID, long coupon) throws CouponSystemExeption {
		couponsDAO.addCouponPurchase(customerID, coupon);
	}

	/**
	 * get all Coupons but Buy from the customer System
	 * 
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCustomerCoupons(long customerID) throws CouponSystemExeption {
		return couponsDAO.readCouponsByCustomer(customerID);
	}

	/**
	 * get all coupons this customer owns from specific category
	 * 
	 * @param categorie - this category will be scanned
	 * @return list of all coupons
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCustomerCouponsByType(long customerID, CouponType type) throws CouponSystemExeption {
		return couponsDAO.readCustomerCouponByType(customerID, type);
	}

	/**
	 * get all coupons this customer owns under a specific price
	 * 
	 * @param maxPrice - max price to get coupons
	 * @return a list of all coupons
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCustomerCouponsByPrice(long customerID, double maxPrice) throws CouponSystemExeption {
		return couponsDAO.readCustomerCouponByPrice(customerID, maxPrice);

	}

	/**
	 * get all Coupons on System
	 * 
	 * @throws Exception
	 */
	public Collection<CouponEntity> readAllCoupons() throws CouponSystemExeption {
			return couponsDAO.readAllCoupons();
	}

	/**
	 * get all coupons from the company that logged in
	 * 
	 * @return a list of coupons this company has
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCompanyCoupons(long CompanyID) throws CouponSystemExeption {
		return couponsDAO.readCouponsByCompany(CompanyID);
	}

	/**
	 * get all coupons this company create under a specific Type
	 * 
	 * @param long companyID CouponType type - id of company and type
	 * @return a list of all coupons
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCompanyCouponsByType(long companyID, CouponType type) throws CouponSystemExeption {
		return couponsDAO.readCompanyCouponByType(companyID, type);
	}

	/**
	 * get all coupons this company create under a specific price
	 * 
	 * @param maxPrice - max price to get coupons
	 * @return a list of all coupons
	 * @throws Exception
	 */
	public Collection<CouponEntity> readCompanyCouponsByPrice(long companyID, double maxPrice) throws CouponSystemExeption {
		return couponsDAO.readCompanyCouponByPrice(companyID, maxPrice);
	}
	
	
	private void ValidateCreateCoupon(CouponEntity coupon)throws CouponSystemExeption {
		LocalDate today = LocalDate.now();
		if (couponsDAO.CheckifCouponExists(coupon.getId())) {
			throw new CouponSystemExeption(ErrorType.COUPON_ID_ALREADY_EXIST);
		}
		if (LocalDate.parse(coupon.getEndDates()).isBefore(today)) {
			throw new CouponSystemExeption(ErrorType.COUPON_EXPIREDE);
		}
		if (!(coupon.getId() > 0 && coupon.getAmount() > 1 && coupon.getDescription() != null && coupon.getTitle() != null
				&& coupon.getImage() != null)) {
			throw new CouponSystemExeption(ErrorType.COUPON_MISS_DETAILS);
		}
		return;
	}

	private void ValidateifCouponUpdatetoCompany(long companyID, CouponEntity coupon)throws CouponSystemExeption {
		LocalDate today = LocalDate.now();
		if (LocalDate.parse(coupon.getEndDates()).isBefore(today)) {
			throw new CouponSystemExeption(ErrorType.COUPON_EXPIREDE);
		}
		if (!(coupon.getId() > 0 && coupon.getEndDates()!=null && coupon.getPrice()!=0)) {
			throw new CouponSystemExeption(ErrorType.COUPON_MISS_DETAILS);
		}
		if(!(couponsDAO.CheckifCompanyHaveCoupon(companyID, coupon.getId()))) {
			throw new CouponSystemExeption(ErrorType.COUPON_GENERAL);
		}
		return;
	}
	

	private void ValidateifCouponBelongsCompany(long companyID, long coupon) throws CouponSystemExeption {
		if(!(couponsDAO.CheckifCompanyHaveCoupon(companyID, coupon))) {
			throw new CouponSystemExeption(ErrorType.COMPANY_TRY_NOTCOUPON);
			}
		return;
	}
	
	private void ValidateifcanPurchasedCoupon(long customer, long coupon) throws CouponSystemExeption {
		CouponEntity checkcoupon = couponsDAO.readOneCoupon(coupon);
		if(couponsDAO.checkifCustomerHaveCoupon(customer, coupon)) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_ALREADY_HAVETHISCOUPON);
		}
		if (!(couponsDAO.CheckifCouponExists(coupon))) {
			throw new CouponSystemExeption(ErrorType.COUPON_NOT_EXIST);
		}
		if(checkcoupon.getAmount()<1) {
			throw new CouponSystemExeption(ErrorType.COUPON_NOT_EXIST);
		}
		return;
	}

}
