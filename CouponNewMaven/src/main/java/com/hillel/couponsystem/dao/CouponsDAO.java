/**
 * coupons DAO
 * @author HillelMed
 */
package com.hillel.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.connectionpool.ConnectionPool;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;
@Repository
public class CouponsDAO implements ICouponsDAO {

	@Override
	public boolean CheckifCouponExists(long coupon) throws CouponSystemExeption {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons WHERE ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, coupon);

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
	@Override
	public boolean CheckifCompanyHaveCoupon(long companyID,long coupon) throws CouponSystemExeption {
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			
			String sql = "SELECT * FROM couponsystem.Coupons WHERE ID=? AND COMPID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, coupon);
				preparedStatement.setLong(2, companyID);
				
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

	/**
	 * checkifCustomerHaveCoupon all the coupons from 
	 * a specific customer and say if have or not
	 * 
	 * @param customerID - this customer's coupons will be returned
	 * @return list of all the coupons that this customer owns
	 * @throws Exception
	 */
	@Override
	public boolean checkifCustomerHaveCoupon(long customerID, long couponID) throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons join couponsystem.Customers_Coupons on Coupons.id = Customers_Coupons.coupon_id where cust_id=? AND coupon_id =?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.setLong(2, couponID);
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

	/**
	 * add coupon to the DB
	 * 
	 * @param coupon - this coupons will be added to the DB
	 * @throws SQLException
	 * @throws SystemConnectionRestore
	 */
	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql ="insert into couponsystem.Coupons(id,category,title,Start_date,end_date,amount,description,price,image,compID) values(?,?,?,?,?,?,?,?,?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, coupon.getId());
				preparedStatement.setString(2, coupon.getCategory().toString());
				preparedStatement.setString(3, coupon.getTitle());
				preparedStatement.setString(4, coupon.getStartDates());
				preparedStatement.setString(5, coupon.getEndDates());
				preparedStatement.setInt(6, coupon.getAmount());
				preparedStatement.setString(7, coupon.getDescription());
				preparedStatement.setDouble(8, coupon.getPrice());
				preparedStatement.setString(9, coupon.getImage());
				preparedStatement.setLong(10, coupon.getCompID());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_CREATE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * update specific coupon
	 * 
	 * @param coupon - this is the new coupon, and it's ID is the coupons in the DB
	 * @throws CouponSystemExeption
	 * @throws SystemConnectionRestore
	 * @throws InterruptedException
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "update couponsystem.Coupons set end_date=?, price=? where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, coupon.getEndDates());
				preparedStatement.setDouble(2, coupon.getPrice());
				preparedStatement.setLong(3, coupon.getId());
				preparedStatement.executeUpdate();

				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_UPDATE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get a specific coupon
	 * 
	 * @param couponID - the coupon with this ID will be returned
	 * @return Coupon
	 */
	@Override
	public Coupon readOneCoupon(long couponID) throws CouponSystemExeption {
	
		Connection connection = null;
	
		try {
			connection = ConnectionPool.getInstance().getConnection();
	
			String sql = "SELECT * FROM couponsystem.Coupons WHERE ID=?";
	
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, couponID);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
	
					if(resultSet.next()) {
						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						return coupon;
					}
					
					return null;
					
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * delete specific coupon
	 * 
	 * @param couponID the coupon with this ID will be deleted from the DB
	 */
	@Override
	public void deleteCoupon(long couponID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "delete from couponsystem.Coupons where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, couponID);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * get all coupons from the DB
	 * 
	 * @return List of all coupons
	 */
	@Override
	public ArrayList<Coupon> readAllCoupons() throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);

					}

					return allCoupons;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * add a purchase of a coupon
	 * 
	 * @param couponID   - this coupon has been bought
	 * @param customerID - this customer bought a coupon
	 */
	@Override
	public void addCouponPurchase(long customerID, long couponID) throws CouponSystemExeption {
	
		Connection connection = null;
	
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO couponsystem.Customers_Coupons(CUST_ID, COUPON_ID) VALUES(?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.setLong(2, couponID);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.COUPON_COUPONPURCHASE);
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * add a coupon --amount of customer buy it
	 * 
	 * @param couponID   - this coupon has been bought
	 * @param customerID - this customer bought a coupon
	 */
	@Override
	public void updateCouponPurchase(Coupon couponID) throws CouponSystemExeption {
		
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "update couponsystem.Coupons set amount=? where id=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, couponID.getAmount());
				preparedStatement.setLong(1, couponID.getId());
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.COUPON_COUPONPURCHASE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	/**
	 * delete specific coupon
	 * 
	 * @param couponID the coupon with this ID will be deleted from the DB
	 */
	@Override
	public void deleteCouponsByCompany(long compID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "delete from couponsystem.Coupons where compID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, compID);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * deletes the purchase of a specific coupon from a customer
	 * 
	 * @param customerID - this customer's purchase will be deleted
	 * @param couponID   - this coupon's purchase will be deleted
	 */
	@Override
	public void deleteCouponPurchaseByCustomer(long customerID,long couponID) throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.Customers_Coupons WHERE Cust_id=? AND Coupon_ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.setLong(2, couponID);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	@Override
	public void deleteCouponPurchase(long couponID) throws CouponSystemExeption {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.Customers_Coupons WHERE Coupon_ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, couponID);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}

		
	}
	/**
	 * deletes the purchase of a specific coupon from a customer
	 * 
	 * @param customerID - this customer's purchase will be deleted
	 * @param couponID   - this coupon's purchase will be deleted
	 */
	@Override
	public void deleteAllCouponPurchase(long couponID) throws CouponSystemExeption {
		
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.Customers_Coupons WHERE COUPON_ID=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, couponID);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_DELETE,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get all the coupons from a specific customer
	 * 
	 * @param customerID - this customer's coupons will be returned
	 * @return list of all the coupons that this customer owns
	 * @throws Exception
	 */
	@Override
	public ArrayList<Coupon> readCouponsByCustomer(long customerID) throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons join couponsystem.Customers_Coupons on Coupons.id = Customers_Coupons.coupon_id where cust_id =?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	/**
	 * get all Coupons specific Company
	 * 
	 * @param companyID ' to go the specific Company
	 * @throws @throws SystemConnectionRestore
	 */
	@Override
	public ArrayList<Coupon> readCouponsByCompany(long CompanyID) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons where compid =?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, CompanyID);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> readCompanyCouponByPrice(long companyID, double price) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons where compid =? AND price=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, companyID);
				preparedStatement.setDouble(2, price);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> readCompanyCouponByType(long companyID, CouponType type) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons where compid =? AND category=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, companyID);
				preparedStatement.setString(2, type.toString());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> readCustomerCouponByPrice(long customerID, double price) throws CouponSystemExeption {
		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons join couponsystem.Customers_Coupons on Coupons.id = Customers_Coupons.coupon_id where cust_id = ? AND coupons.price <= ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.setDouble(2, price);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> readCustomerCouponByType(long customerID, CouponType type) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.Coupons join couponsystem.Customers_Coupons on Coupons.id = Customers_Coupons.coupon_id where cust_id =? coupons.category=?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, customerID);
				preparedStatement.setString(2, type.toString());
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while (resultSet.next()) {

						Coupon coupon = Utilextractor.getResultSetCoupon(resultSet);
						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ,e.getMessage());
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
}
