/**
 * Customer DAO
 * @author HillelMed
 */
package com.hillel.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.beans.Customer;
import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.connectionpool.ConnectionPool;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;
@Repository
public class CustomersDAO implements ICustomersDAO {
	
	@Autowired
	private ICouponsDAO couponsdao;

	/**
	 * add a customer to the DB
	 * 
	 * @param customer - this customer will be added to the DB
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql ="insert into couponsystem.Customers(id,custname,password,email) values(?,?,?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customer.getId());
				preparedStatement.setString(2, customer.getCustName());
				preparedStatement.setLong(3, customer.getPassword().hashCode());
				preparedStatement.setString(4, customer.getEmail());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_CREATE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * update a specific customer's details
	 * 
	 * @param customer - this customer's details will be updated
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "update couponsystem.Customers set custname=?, password =?, email=? where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, customer.getCustName());
				preparedStatement.setLong(2, customer.getPassword().hashCode());
				preparedStatement.setString(3, customer.getEmail());
				preparedStatement.setLong(4, customer.getId());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_UPDATE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}

	}

	/**
	 * get one specific customer by email
	 * 
	 * @param customer - the customer with this EMAIL will be returned
	 * @return customer 
	 */
	@Override
	public Customer readCustomer(long customer) throws CouponSystemExeption {

		Connection connection = null;

		try {

			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Customers WHERE ID=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				
				preparedStatement.setLong(1, customer);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					if(resultSet.next()) {

					Customer customernew = Utilextractor.getResultSetCustomer(resultSet);
					return customernew;
					}
					return null;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	
	/**
	 * delete a specific customer from DB
	 * 
	 * @param customerID - this customer will be deleted
	 */
	@Override
	public void deleteCustomer(long customerID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "delete from couponsystem.customers where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				ArrayList<Coupon> arrcouponcustomer = couponsdao.readCouponsByCustomer(customerID);
				if(arrcouponcustomer!=null) {
					for (Coupon coupon : arrcouponcustomer) {
						preparedStatement.addBatch("DELETE FROM couponsystem.Customers_Coupons WHERE Cust_id="+customerID + " AND Coupon_ID=" + coupon.getId());
					}
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
	 * delete all the customer's purchase history from the DB
	 * 
	 * @param customerID - this customer's purchase history will be deleted
	 */
	 @Override
	public void deleteCustomerBuy(long customerID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql ="delete from couponsystem.Customers_Coupons where Cust_ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get all the customers from the DB
	 * 
	 * @return list of all customers
	 */
	@Override
	public ArrayList<Customer> readAllCustomer() throws CouponSystemExeption {
	
		Connection connection = null;
	
		try {
	
			connection = ConnectionPool.getInstance().getConnection();
	
			String sql = "SELECT * FROM couponsystem.Customers";
	
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
	
					ArrayList<Customer> allCustomers = new ArrayList<Customer>();
	
					while (resultSet.next()) {
						
						Customer customer = Utilextractor.getResultSetCustomer(resultSet);
						
						allCustomers.add(customer);
					}
					return allCustomers;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * Check if a specific customer is in the DB
	 * 
	 * @param email    - customer's email
	 * @param password - customer's password
	 * @return true if exist, false if not
	 */
	@Override
	public Userdetails login(Userdetails userlogin) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql ="SELECT id,email,password FROM couponsystem.Customers WHERE email=? AND password=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, userlogin.getEmail());
				preparedStatement.setLong(2, userlogin.getPassword().hashCode());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					if(resultSet.next()) {
					long id = resultSet.getLong("ID");
					userlogin.setId(id);
					return userlogin;
					}
					    userlogin.setId(-1);
						return userlogin;
					}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_LOGGIN,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	@Override
	public boolean CheckifCustomerExists(long customer) throws CouponSystemExeption {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Customers WHERE ID=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customer);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					return resultSet.next();
					
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO);
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}


}
