package com.hillel.couponsystem.api;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hillel.couponsystem.entitiy.CompanyEntity;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.logic.CompanyController;
import com.hillel.couponsystem.util.Utilextractor;


@RestController
@RequestMapping(value = "/companies")
public class companiesApi {

	@Autowired
	private CompanyController companycontroller;

	@RequestMapping(method = RequestMethod.POST)
	public void createCompany(HttpServletRequest req,@RequestBody CompanyEntity company) throws CouponSystemExeption {
		companycontroller.createCompany(company);
	}
	@RequestMapping(method = RequestMethod.GET)
	public CompanyEntity readCompanydetails(HttpServletRequest req) throws CouponSystemExeption {
		long companyID = Long.parseLong(Utilextractor.getCookieServer(req.getCookies()).getValue());
		return companycontroller.readCompanyDetails(companyID);
	}
	@GetMapping("/{id}")
	public CompanyEntity readCompanydetailsJPA(@PathVariable("id") long id ,HttpServletRequest req) throws CouponSystemExeption {
		return companycontroller.readCompanyDetails(id);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateCompany(HttpServletRequest req,@RequestBody CompanyEntity company) throws CouponSystemExeption {
		companycontroller.updateCompany(company);
	}

	@RequestMapping(value=("/{comapnyID}") ,method = RequestMethod.DELETE)
	public void deleteCompanyByid(HttpServletRequest req,@PathVariable("comapnyID") long companyid) throws CouponSystemExeption {
		companycontroller.deleteCompany(companyid);
	}
	@RequestMapping(value=("/readall"),method = RequestMethod.GET)
	public Collection<CompanyEntity> readallCompany(HttpServletRequest req) throws CouponSystemExeption {
		return companycontroller.readAllCompanies();
	}


}
