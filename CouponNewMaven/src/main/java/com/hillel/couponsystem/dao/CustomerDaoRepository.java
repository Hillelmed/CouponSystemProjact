package com.hillel.couponsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hillel.couponsystem.entitiy.CustomerEntity;

public interface CustomerDaoRepository extends JpaRepository<CustomerEntity,Long>{

}
