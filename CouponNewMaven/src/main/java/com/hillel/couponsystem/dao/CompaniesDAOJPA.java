package com.hillel.couponsystem.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.entitiy.CompanyEntity;
import com.hillel.couponsystem.entitiy.CouponEntity;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

@Repository
public class CompaniesDAOJPA {

	@PersistenceContext(unitName = "coupondb")
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRED)
	public void createCompany(CompanyEntity company) throws CouponSystemExeption {
		entityManager.persist(company);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) throws CouponSystemExeption {
		entityManager.merge(company);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCompany(long companyID) throws CouponSystemExeption {
		CompanyEntity ce = entityManager.find(CompanyEntity.class, companyID);
		entityManager.remove(ce);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CompanyEntity readCompany(long companyID) throws CouponSystemExeption {
		CompanyEntity ce = entityManager.find(CompanyEntity.class, companyID);
		if (ce == null) {
			throw new CouponSystemExeption(ErrorType.COMPANY_NOT_EXIST);
		}
		Query quare = entityManager.createQuery("FROM CouponEntity ce WHERE ce.id=:id").setParameter("id",companyID);
		@SuppressWarnings("unchecked")
		Collection<CouponEntity> couponofCompany = quare.getResultList();
		ce.setCoupons(couponofCompany);
		return ce;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<CompanyEntity> readAllCompanies() throws CouponSystemExeption {
		String hql = "FROM CompanyEntity";
		Query query = entityManager.createQuery(hql);
		@SuppressWarnings("unchecked")
		Collection<CompanyEntity> allCompanies = query.getResultList();
		return allCompanies;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Userdetails login(Userdetails userdetails) throws CouponSystemExeption {
		String hql = "FROM CompanyEntity ce where ce.email=:email AND ce.password=:password";
		
		Query query = entityManager.createQuery(hql).setParameter("email",userdetails.getEmail()).setParameter("password", userdetails.getPassword());
		
		CompanyEntity companynow = (CompanyEntity) query.getSingleResult();
		if(companynow == null) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
		}
        userdetails.setId(companynow.getId());
		return userdetails;
	}

	public boolean CheckifCompanyExists(long companyID) throws CouponSystemExeption {
		CompanyEntity ce = entityManager.find(CompanyEntity.class, companyID);
		if (ce != null) {
			return true;
		}
		return false;
	}

}
