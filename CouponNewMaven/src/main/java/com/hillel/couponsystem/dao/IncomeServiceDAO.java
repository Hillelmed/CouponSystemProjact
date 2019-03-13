package com.hillel.couponsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hillel.couponsystem.beans.Income;
import com.hillel.couponsystem.connectionpool.ConnectionPool;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.enums.IncomeType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

@Repository
public class IncomeServiceDAO {

	public void storeIncome (Income income) throws CouponSystemExeption {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql ="insert into couponsystem.coupons(id,name,description,date,amount) values(?,?,?,?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, income.getId());
				preparedStatement.setString(2, income.getName());
				preparedStatement.setString(3, income.getDescription().toString());
				preparedStatement.setString(4, income.getDate().toString());
				preparedStatement.setDouble(5, income.getAmount());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_CREATE);
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	public ArrayList<Income> viewAllIncome() throws CouponSystemExeption {

		Connection connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM couponsystem.income";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Income> allincome = new ArrayList<>();

					while (resultSet.next()) {
                        long id = resultSet.getLong(1);
                        String name = resultSet.getString(2);
                        IncomeType type = IncomeType.valueOf(resultSet.getString(3));
                        Date date = Date.valueOf(resultSet.getString(4));
                        double amount = resultSet.getDouble(5);
                        Income income = new Income(id, name, type, date, amount);
                        
                        allincome.add(income);
						
					}

					return allincome;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ);
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	public ArrayList<Income> viewIncomeByCustomer(String Customeremail) throws CouponSystemExeption {
		
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			
			String sql = "SELECT * FROM couponsystem.income WHERE name=?";
			
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				
				preparedStatement.setString(1,Customeremail);
				
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					
					ArrayList<Income> allincome = new ArrayList<>();
					
					while (resultSet.next()) {
						long id = resultSet.getLong(1);
						String name = resultSet.getString(2);
						IncomeType type = IncomeType.valueOf(resultSet.getString(3));
						Date date = Date.valueOf(resultSet.getString(4));
						double amount = resultSet.getDouble(5);
						Income income = new Income(id, name, type, date, amount);
						
						allincome.add(income);
						
					}
					
					return allincome;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ);
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}
	public ArrayList<Income> viewIncomeByCompany(String Companyemail) throws CouponSystemExeption {
		
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			
			String sql = "SELECT * FROM couponsystem.income WHERE name=?";
			
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				
				preparedStatement.setString(1, Companyemail);
				
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					
					ArrayList<Income> allincome = new ArrayList<>();
					
					while (resultSet.next()) {
						long id = resultSet.getLong(1);
						String name = resultSet.getString(2);
						IncomeType type = IncomeType.valueOf(resultSet.getString(3));
						Date date = Date.valueOf(resultSet.getString(4));
						double amount = resultSet.getDouble(5);
						Income income = new Income(id, name, type, date, amount);
						
						allincome.add(income);
						
					}
					
					return allincome;
				}
			} catch (SQLException e) {
				throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_DAO_READ);
			}
		} finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}


}
