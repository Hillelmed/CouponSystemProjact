package com.hillel.couponsystem.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.dao.CustomerDaoRepository;
import com.hillel.couponsystem.dao.CustomersDAO;
import com.hillel.couponsystem.dao.CustomersDAOJPA;
import com.hillel.couponsystem.entitiy.CustomerEntity;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;

/**
 * Customer facade control the System by function customer
 * 
 * @author HillelMed
 */
@Controller
public class CustomerController {


	@Autowired
	private CustomersDAOJPA customersDAO;
	
	@SuppressWarnings("unused")
	@Autowired
	private CustomerDaoRepository customerDAoRepository;
	
//	@Autowired
//	private ICustomersDAO customersDAO;

	/**
	 * add a customer to the DB
	 * 
	 * @param customer - this customer will be added to DB
	 * @throws Exception
	 * @throws CustomerEmailAlreadyExistsException
	 */

	public void createCustomer(CustomerEntity customer) throws CouponSystemExeption {
		CheckcanCreateCustomer(customer);
		customersDAO.createCustomer(customer);
	}

	/**
	 * get all the details about the customer that logged in
	 * 
	 * @return customer's details
	 * @throws Exception
	 */
	public CustomerEntity readCustomerDetails(long customerID) throws CouponSystemExeption {
			return customersDAO.readCustomer(customerID);
	}

	/**
	 * update a specific customer's details
	 * 
	 * @param customer - this customer will be updated
	 * @throws Exception
	 * @throws CustomerNotExisxt
	 */
	public void updateCustomer(CustomerEntity customer) throws CouponSystemExeption {
		    CheckcanUpdateCustomer(customer);
			customersDAO.updateCustomer(customer);
	}

	/**
	 * delete a specific customer
	 * 
	 * @param customerID - the customer with this ID will be deleted
	 * @throws Exception
	 */
	public void deleteCustomer(long customerID) throws CouponSystemExeption {
		CheckcanDeleteCustomer(customerID);
		customersDAO.deleteCustomer(customerID);
	}

	/**
	 * get all customers from DB
	 * 
	 * @return a list of all customers
	 * @throws Exception
	 */
	public Collection<CustomerEntity> readAllCustomers() throws CouponSystemExeption {
			return customersDAO.readAllCustomer();
		}

	/**
	 * check if this user is a specific customer and set the customer id of this
	 * 
	 * @param email    - entered email
	 * @param password - entered password
	 * @return true if a customer, false if not
	 */
	public Userdetails login(Userdetails userdeatils) throws CouponSystemExeption {
		return userdeatils = customersDAO.login(userdeatils);
	}
	
	private void CheckcanCreateCustomer(CustomerEntity customer) throws CouponSystemExeption {
		if (customersDAO.CheckifCustomerExists(customer.getId())) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_ALREADY_EXIST);
		}
		if(!Utilextractor.isValidEmailAddress(customer.getEmail())) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_EMAIL_EXIST);
		}
		if(customer.getPassword()<3) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_PASSWORDWEAK);
		}
		return;
	}
	private void CheckcanUpdateCustomer(CustomerEntity customer) throws CouponSystemExeption {
		CustomersDAO customersDAO = new CustomersDAO();
		if (!customersDAO.CheckifCustomerExists(customer.getId())) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_NOT_EXIST);
		}
		if(!Utilextractor.isValidEmailAddress(customer.getEmail())) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_EMAIL_EXIST);
		}
		if(customer.getPassword()<3) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_PASSWORDWEAK);
		}
		return;
	}
	private void CheckcanDeleteCustomer(long customerID) throws CouponSystemExeption {
		if (!customersDAO.CheckifCustomerExists(customerID)) {
			throw new CouponSystemExeption(ErrorType.CUSTOMER_NOT_EXIST);
		}
		return;
	}

}
