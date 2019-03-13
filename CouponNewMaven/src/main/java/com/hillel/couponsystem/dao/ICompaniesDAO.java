package com.hillel.couponsystem.dao;
import java.util.ArrayList;

import com.hillel.couponsystem.beans.*;
import com.hillel.couponsystem.exception.CouponSystemExeption;


public interface ICompaniesDAO {
	void createCompany(Company company)throws CouponSystemExeption;
	void updateCompany(Company company) throws CouponSystemExeption;
	void deleteCompany(long companyID)throws CouponSystemExeption;
	Company readCompany(long companyID) throws CouponSystemExeption;
	ArrayList<Company> readAllCompanies() throws CouponSystemExeption;
	Userdetails login(Userdetails userdetails) throws CouponSystemExeption;
	boolean CheckifCompanyExists(long companyID) throws CouponSystemExeption;
}
