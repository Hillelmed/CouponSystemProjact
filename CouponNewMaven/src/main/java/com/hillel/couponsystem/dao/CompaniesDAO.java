/**
 * Companies DAO
 * @author HillelMed
 */
package com.hillel.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hillel.couponsystem.beans.Company;
import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.connectionpool.ConnectionPool;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;
@Repository
public class CompaniesDAO implements ICompaniesDAO {
	/**
	 * add comapny to the DB
	 * 
	 * @param company - company to push inside DB
	 * @throws CouponSystemExeption
	 * @throws InterruptedException
	 */
	@Autowired
	private ICouponsDAO couponsdao;

	public CompaniesDAO(){};
	@Override
	public void createCompany(Company company) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "insert into couponsystem.Companies(id,name,password,email) values(?,?,?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, company.getId());
				preparedStatement.setString(2, company.getName());
				preparedStatement.setLong(3, company.getPassword().hashCode());
				preparedStatement.setString(4, company.getEmail());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_CREATE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * update specific company in the DB
	 * 
	 * @param company - company to update
	 * @throws InterruptedException
	 * @throws SystemConnectionRestore
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemExeption {
		Connection connection = null;
		connection = ConnectionPool.getInstance().getConnection();
		try {
			String sql = "update couponsystem.Companies set name=?, password=?,email=? where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, company.getName());
				preparedStatement.setLong(2, company.getPassword().hashCode());;
				preparedStatement.setString(3, company.getEmail());
				preparedStatement.setLong(4, company.getId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_UPDATE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get specific company
	 * 
	 * @param companyID - return this specific company
	 * @return Company which owns the companyID
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemConnectionRestore
	 * @throws ParseException
	 */
	@Override
	public Company readCompany(long companyID) throws CouponSystemExeption {
	
		Connection connection = null;
		connection = ConnectionPool.getInstance().getConnection();
	
		try {
	
			String sql ="SELECT * FROM couponsystem.Companies WHERE ID=?";
	
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, companyID);
	
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					
					if(resultSet.next()) {
						
					 Company company = Utilextractor.getResultSetComapny(resultSet);
	
					return company;
					}
					throw new CouponSystemExeption(ErrorType.COMPANY_NOT_EXIST);
					
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * delete specific company from the DB
	 * 
	 * @param companyID -the company with this ID will be deleted
	 * @throws SQLException
	 * @throws SystemConnectionRestore
	 */
	@Override
	public void deleteCompany(long companyID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql ="delete from couponsystem.Companies where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, companyID);
				ArrayList<Coupon> coupons = couponsdao.readCouponsByCompany(companyID);
				if(coupons!=null) {
				for (int i = 0; i <coupons.size() ; i++) {
					preparedStatement.addBatch("DELETE FROM couponsystem.Customers_Coupons WHERE COUPON_ID=" + coupons.get(i).getId());
				}
				preparedStatement.addBatch("delete from couponsystem.Coupons where compID=" + companyID);
				preparedStatement.executeBatch();
				}
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * bring all the DB's companies
	 * 
	 * @return array list of all the companies in the DB
	 */
	@Override
	public ArrayList<Company> readAllCompanies() throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Companies";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Company> allCompanies = new ArrayList<Company>();

					while (resultSet.next()) {
                        Company company = Utilextractor.getResultSetComapny(resultSet);
						allCompanies.add(company);
					}
					return allCompanies;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get True or false if have This Login *
	 * 
	 * @param Company Email and Password - the company with this ID
	 * @return boolean
	 * @throws SystemConnectionRestore
	 * 
	 */
	@Override
	public Userdetails login(Userdetails userdetails) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql ="SELECT id,email,password FROM couponsystem.Companies WHERE email=? AND password=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, userdetails.getEmail());
				preparedStatement.setLong(2, userdetails.getPassword().hashCode());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					if(resultSet.next()) {
					long id = resultSet.getLong("ID");
					userdetails.setId(id);
					return userdetails;
					}
					    userdetails.setId(-1);
						return userdetails;
					}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_LOGGIN,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public boolean CheckifCompanyExists(long company) throws CouponSystemExeption {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql ="SELECT * FROM couponsystem.Companies WHERE ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, company);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					return resultSet.next();
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

}
