package com.hillel.couponsystem.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.dao.CompaniesDAOJPA;
import com.hillel.couponsystem.entitiy.CompanyEntity;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;

/**
 * Company's facade control the Company function
 * 
 * @author HillelMed
 */
@Controller
public class CompanyController {

	/**
	 * 
	 */
//	@Autowired
//	private ICompaniesDAO companiesDAO;
	@Autowired
	private CompaniesDAOJPA companydaoJpa;


	/**
	 * add company to DB
	 * 
	 * @param company - this company will be added
	 * @throws Exception
	 */
	public void createCompany(CompanyEntity company) throws CouponSystemExeption {
		ValidateCompnayCreate(company);
		companydaoJpa.createCompany(company);
	}

	/**
	 * get all the details about the company that logged in
	 * 
	 * @return company's details
	 * @throws Exception
	 */
	public CompanyEntity readCompanyDetails(long companyID) throws CouponSystemExeption {
		return companydaoJpa.readCompany(companyID);
	}
	
	/**
	 * update a specific company
	 * 
	 * @param company - this company will be updated with a new details
	 * @throws Exception
	 */
	public void updateCompany(CompanyEntity company) throws CouponSystemExeption {
	        ValidateCompnayUpdate(company);
	        companydaoJpa.updateCompany(company);
	}

	/**
	 * delete a specific company
	 * 
	 * @param company - this company will be deleted
	 * @throws Exception
	 */

	public void deleteCompany(long company) throws CouponSystemExeption {
		    ValidateCompnayDelete(company);
		    companydaoJpa.deleteCompany(company);
	}

	public Collection<CompanyEntity> readAllCompanies() throws CouponSystemExeption {
		return companydaoJpa.readAllCompanies();
	}

	/**
	 * check if this user is a specific company
	 * @param email    - entered email
	 * @param password - entered password
	 * @return true if a company, false if not
	 * @throws CouponSystemExeption
	 */
	public Userdetails login(Userdetails userdeatils) throws CouponSystemExeption {
			return userdeatils = companydaoJpa.login(userdeatils);
	}
	
	
	private void ValidateCompnayCreate(CompanyEntity company) throws CouponSystemExeption {
		
		if (company == null) {
			throw new CouponSystemExeption("Something missing");
		}
		if (companydaoJpa.CheckifCompanyExists(company.getId())) {
			throw new CouponSystemExeption(ErrorType.COMPANY_ALREADY_EXIST);
		}
		if (!Utilextractor.isValidEmailAddress(company.getEmail())) {
			throw new CouponSystemExeption(ErrorType.COMPANY_NAME_EXIST);
		}
		if(company.getPassword()<3) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_PASSWORDWEAK);
		}
		return;
	}
	private void ValidateCompnayUpdate(CompanyEntity company) throws CouponSystemExeption {
		if (company == null) {
			throw new CouponSystemExeption(ErrorType.COMPANY_GENERAL);
		}
		if (!companydaoJpa.CheckifCompanyExists(company.getId())) {
			throw new CouponSystemExeption(ErrorType.COMPANY_ALREADY_EXIST);
		}
		if (!Utilextractor.isValidEmailAddress(company.getEmail())) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_EMAILWEAK);
		}
		if(company.getPassword()<3) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_PASSWORDWEAK);
		}
		return;
	}
	private void ValidateCompnayDelete(long customerID) throws CouponSystemExeption {
		if (!companydaoJpa.CheckifCompanyExists(customerID)) {
			throw new CouponSystemExeption(ErrorType.COMPANY_NOT_EXIST);
		}
		return;
	}

}
