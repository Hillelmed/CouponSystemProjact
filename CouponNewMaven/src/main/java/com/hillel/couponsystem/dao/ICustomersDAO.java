package com.hillel.couponsystem.dao;
import java.util.ArrayList;

import com.hillel.couponsystem.beans.Customer;
import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.exception.CouponSystemExeption;

public interface ICustomersDAO {
	void createCustomer(Customer customer)throws CouponSystemExeption;
	void updateCustomer(Customer customer) throws CouponSystemExeption;
	void deleteCustomer(long customer)throws CouponSystemExeption;
	void deleteCustomerBuy(long customerID) throws CouponSystemExeption;
	Customer readCustomer(long customer) throws CouponSystemExeption;
	ArrayList<Customer> readAllCustomer() throws CouponSystemExeption;
	Userdetails login(Userdetails userdetails) throws CouponSystemExeption;
	boolean CheckifCustomerExists(long customer) throws CouponSystemExeption;
}
