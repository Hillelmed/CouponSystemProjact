/**
 * coupons DAO
 * @author HillelMed
 */
package com.hillel.couponsystem.dao;

import java.sql.SQLException;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hillel.couponsystem.entitiy.CompanyEntity;
import com.hillel.couponsystem.entitiy.CouponEntity;
import com.hillel.couponsystem.entitiy.CustomerEntity;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
@Repository
public class CouponsDAOJPA{
	
	@PersistenceContext(unitName = "coupondb")
	private EntityManager entityManager;


	public boolean CheckifCouponExists(long coupon) throws CouponSystemExeption {
			String sql = "FROM CouponEntity c WHERE c.ID=:id";
			Query query = entityManager.createQuery(sql).setParameter("id", coupon);
			CouponEntity couponnow = (CouponEntity) query.getSingleResult();
			if(couponnow == null) {
              return false;				
			}
			return true;
	}
	public boolean CheckifCompanyHaveCoupon(long companyID,long coupon) throws CouponSystemExeption {
		String sql = "FROM CouponEntity c WHERE c.ID=:id AND c.COMPID_ID=:idcomp";
		Query query = entityManager.createQuery(sql).setParameter("id",coupon).setParameter("idcomp",companyID);
		CouponEntity couponnow = (CouponEntity) query.getSingleResult();
		if(couponnow == null) {
          return false;				
		}
		return true;
	}

	/**
	 * checkifCustomerHaveCoupon all the coupons from 
	 * a specific customer and say if have or not
	 * 
	 * @param customerID - this customer's coupons will be returned
	 * @return list of all the coupons that this customer owns
	 * @throws Exception
	 */
	public boolean checkifCustomerHaveCoupon(long customerID, long couponID) throws CouponSystemExeption {
		String sql = "FROM CustomerEntity c WHERE c.ID=:id AND c.coupons=:idcoupon";
		Query query = entityManager.createQuery(sql).setParameter("id",customerID).setParameter("idcomp",couponID);
		CouponEntity couponnow = (CouponEntity) query.getSingleResult();
		if(couponnow == null) {
          return false;				
		}
		return true;
	}

	/**
	 * add coupon to the DB
	 * 
	 * @param coupon - this coupons will be added to the DB
	 * @throws SQLException
	 * @throws SystemConnectionRestore
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void createCoupon(CouponEntity coupon) throws CouponSystemExeption {
		entityManager.persist(coupon);
	}

	/**
	 * update specific coupon
	 * 
	 * @param coupon - this is the new coupon, and it's ID is the coupons in the DB
	 * @throws CouponSystemExeption
	 * @throws SystemConnectionRestore
	 * @throws InterruptedException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCoupon(CouponEntity coupon) throws CouponSystemExeption {
		entityManager.merge(coupon);
	}

	/**
	 * get a specific coupon
	 * 
	 * @param couponID - the coupon with this ID will be returned
	 * @return Coupon
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CouponEntity readOneCoupon(long couponID) throws CouponSystemExeption {
		CouponEntity ce = entityManager.find(CouponEntity.class, couponID);
		if(ce == null) {
			throw new CouponSystemExeption(ErrorType.COUPON_NOT_EXIST);
		}
		return ce;
	}
	/**
	 * delete specific coupon
	 * 
	 * @param couponID the coupon with this ID will be deleted from the DB
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCoupon(long couponID) throws CouponSystemExeption {
		CouponEntity ce = entityManager.find(CouponEntity.class, couponID);
		entityManager.remove(ce);
	}
	/**
	 * get all coupons from the DB
	 * 
	 * @return List of all coupons
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readAllCoupons() throws CouponSystemExeption {
		String hql = "FROM CouponEntity";
		Query query = entityManager.createQuery(hql);
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> allcoupons = query.getResultList();
		return allcoupons;
	}

	/**
	 * add a purchase of a coupon
	 * 
	 * @param couponID   - this coupon has been bought
	 * @param customerID - this customer bought a coupon
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addCouponPurchase(long customerID, long couponID) throws CouponSystemExeption {
	   CustomerEntity ce = entityManager.getReference(CustomerEntity.class, customerID);
	   CouponEntity coupone = entityManager.find(CouponEntity.class, couponID);
	   ce.getCoupons().add(coupone);
	}
	/**
	 * add a coupon --amount of customer buy it
	 * 
	 * @param couponID   - this coupon has been bought
	 * @param customerID - this customer bought a coupon
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCouponPurchase(CouponEntity couponID) throws CouponSystemExeption {
		CouponEntity ce = entityManager.getReference(CouponEntity.class, couponID);
		ce.setAmount(couponID.getAmount());
		ce.setEndDates(couponID.getEndDates());
	}
	/**
	 * delete specific coupon
	 * 
	 * @param couponID the coupon with this ID will be deleted from the DB
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCouponsByCompany(long compID) throws CouponSystemExeption {
		CompanyEntity ce = entityManager.find(CompanyEntity.class, compID);
		entityManager.remove(ce);
	}
	
	/**
	 * deletes the purchase of a specific coupon from a customer
	 * 
	 * @param customerID - this customer's purchase will be deleted
	 * @param couponID   - this coupon's purchase will be deleted
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCouponPurchaseByCustomer(long customerID,long couponID) throws CouponSystemExeption {
		CustomerEntity ce = entityManager.getReference(CustomerEntity.class, customerID);
		CouponEntity coupon = entityManager.getReference(CouponEntity.class, couponID);
		coupon.getCustomers().remove(ce);
	}
	/**
	 * deletes the purchase of a specific coupon from a customer
	 * 
	 * @param customerID - this customer's purchase will be deleted
	 * @param couponID   - this coupon's purchase will be deleted
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllCouponPurchase(long couponID) throws CouponSystemExeption {
		CouponEntity coupon = entityManager.getReference(CouponEntity.class, couponID);
        coupon.setCustomers(null);
	}

	/**
	 * get all the coupons from a specific customer
	 * 
	 * @param customerID - this customer's coupons will be returned
	 * @return list of all the coupons that this customer owns
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCouponsByCustomer(long customerID) throws CouponSystemExeption {
	  CustomerEntity ce = entityManager.find(CustomerEntity.class, customerID);
	  return ce.getCoupons();
	}

	/**
	 * get all Coupons specific Company
	 * 
	 * @param companyID ' to go the specific Company
	 * @throws @throws SystemConnectionRestore
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCouponsByCompany(long CompanyID) throws CouponSystemExeption {
		CompanyEntity ce = entityManager.find(CompanyEntity.class, CompanyID);
		return ce.getCoupons();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCompanyCouponByPrice(long companyID, double price) throws CouponSystemExeption {
		String hql = "FROM CouponEntity as coupons WHERE coupons.company.id=:id AND coupons.price <= :price";
		Query query = entityManager.createQuery(hql)
				.setParameter("id", companyID)
				.setParameter("price", price);
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> coupons = query.getResultList();
		return coupons;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCompanyCouponByType(long companyID, CouponType type) throws CouponSystemExeption {
		String hql = "FROM CouponEntity as coupons WHERE coupons.company.id=:id AND coupons.type = :type";
		Query query = entityManager.createQuery(hql)
				.setParameter("id", companyID)
				.setParameter("type", type.toString());
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> coupons = query.getResultList();
		return coupons;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCustomerCouponByPrice(long customerID, double price) throws CouponSystemExeption {
		String hql = "FROM CouponEntity as coupons INNER JOIN coupons.customers as customer WHERE customer.id=:id AND coupons.price <= :price";
		Query query = entityManager.createQuery(hql)
				.setParameter("id", customerID)
				.setParameter("price", price);
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> coupons = query.getResultList();
		return coupons;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CouponEntity> readCustomerCouponByType(long customerID, CouponType type) throws CouponSystemExeption {
		String hql = "FROM CouponEntity as coupons INNER JOIN coupons.customers as customer WHERE customer.id=:id AND coupons.category = :type";
		Query query = entityManager.createQuery(hql)
				.setParameter("id", customerID)
				.setParameter("type", type.toString());
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> coupons = query.getResultList();
		return coupons;
	}
}
