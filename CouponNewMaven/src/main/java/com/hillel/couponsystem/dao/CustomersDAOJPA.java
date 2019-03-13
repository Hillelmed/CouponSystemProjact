/**
 * Customer DAO
 * @author HillelMed
 */
package com.hillel.couponsystem.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.entitiy.CouponEntity;
import com.hillel.couponsystem.entitiy.CustomerEntity;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

@Repository
public class CustomersDAOJPA {

	@PersistenceContext(unitName = "coupondb")
	private EntityManager entityManager;

//	@Autowired
//	private ICouponsDAO couponsdao;

	/**
	 * add a customer to the DB
	 * 
	 * @param customer - this customer will be added to the DB
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void createCustomer(CustomerEntity customer) throws CouponSystemExeption {
		entityManager.persist(customer);
	}

	/**
	 * update a specific customer's details
	 * 
	 * @param customer - this customer's details will be updated
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCustomer(CustomerEntity customer) throws CouponSystemExeption {
		entityManager.merge(customer);
	}

	/**
	 * get one specific customer by email
	 * 
	 * @param customer - the customer with this EMAIL will be returned
	 * @return customer
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerEntity readCustomer(long customer) throws CouponSystemExeption {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customer);
		if (ce == null) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_NOT_EXIST);
		}
		String hql = "FROM CouponEntity as coupons INNER JOIN coupons.customers as customer WHERE customer.id=:id";
		Query query = entityManager.createQuery(hql).setParameter("id", customer);
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> coupons = query.getResultList();
		if (coupons == null) {
			return ce;
		}
		ce.setCoupons(coupons);
		return ce;
	}

	/**
	 * delete a specific customer from DB
	 * 
	 * @param customerID - this customer will be deleted
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCustomer(long customerID) throws CouponSystemExeption {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customerID);
		entityManager.remove(ce);
	}

	/**
	 * get all the customers from the DB
	 * 
	 * @return list of all customers
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CustomerEntity> readAllCustomer() throws CouponSystemExeption {
		String hql = "FROM CustomerEntity";
		Query query = entityManager.createQuery(hql);
		@SuppressWarnings("unchecked")
		Collection<CustomerEntity> allCustomer = query.getResultList();
		return allCustomer;
	}

	/**
	 * Check if a specific customer is in the DB
	 * 
	 * @param email    - customer's email
	 * @param password - customer's password
	 * @return true if exist, false if not
	 */
	public Userdetails login(Userdetails userdetails) throws CouponSystemExeption {
		String hql = "FROM CustomerEntity ce where ce.email=:email AND ce.password=:password";

		Query query = entityManager.createQuery(hql).setParameter("email", userdetails.getEmail())
				.setParameter("password", userdetails.getPassword());

		CustomerEntity customernow = (CustomerEntity) query.getSingleResult();
		if (customernow == null) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
		}
		userdetails.setId(customernow.getId());
		return userdetails;
	}

	public boolean CheckifCustomerExists(long customer) throws CouponSystemExeption {
		CustomerEntity ce = entityManager.find(CustomerEntity.class, customer);
		if (ce != null) {
			return true;
		}
		return false;
	}

}
