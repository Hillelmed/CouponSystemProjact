package com.hillel.couponsystem.api;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hillel.couponsystem.entitiy.CustomerEntity;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.logic.CustomerController;
import com.hillel.couponsystem.util.Utilextractor;

@RestController
@RequestMapping("/customer")
public class customerApi {

    @Autowired
	private CustomerController customercontroller;


    @RequestMapping(method = RequestMethod.POST)
	public void createCustomer(HttpServletRequest req,@RequestBody CustomerEntity customer) throws CouponSystemExeption {
		customercontroller.createCustomer(customer);
	}

    @RequestMapping(method = RequestMethod.GET)
	public CustomerEntity readCustomerdetails(HttpServletRequest req) throws CouponSystemExeption {
		long customerID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return customercontroller.readCustomerDetails(customerID);
	}

    @RequestMapping(method = RequestMethod.PUT)
	public void updateCustomer(HttpServletRequest req,@RequestBody CustomerEntity customer) throws CouponSystemExeption {
		customercontroller.updateCustomer(customer);
	}

    @RequestMapping(value = "/{customerID}",method = RequestMethod.DELETE)
	public void deleteCustomerByid(HttpServletRequest req,@PathVariable("customerID") long customerid) throws CouponSystemExeption {
		customercontroller.deleteCustomer(customerid);
	}

    @RequestMapping(value = "/readall",method = RequestMethod.GET)
	public Collection<CustomerEntity> readallCustomer(HttpServletRequest req) throws CouponSystemExeption {
		return customercontroller.readAllCustomers();
	}
}
