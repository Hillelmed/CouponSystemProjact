package com.hillel.couponsystem.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hillel.couponsystem.beans.Company;
import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.beans.Customer;
import com.hillel.couponsystem.dao.CouponsDAO;
import com.hillel.couponsystem.enums.CouponType;
import com.hillel.couponsystem.exception.CouponSystemExeption;

public class Utilextractor {

	/*
	 * / This method get the IP from request and the id and check if the same IP ID
	 * generate
	 * 
	 * @param HttpServletRequest request,long id
	 * 
	 * @return String of IP and ID
	 */
	public static String getClientIp(HttpServletRequest request, long id) {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		remoteAddr += " ID : " + id;
		return remoteAddr;
	}

	public static Cookie getCookieServer(Cookie[] arrcookie) {
		try {
			Cookie[] a1 = arrcookie;
			if (a1 != null) {
				for (int i = 0; i < arrcookie.length; i++) {
					if (a1[i].getName().equals("Customerlog") || (a1[i].getName().equals("Companylog"))
							|| a1[i].getName().equals("Adminlog")) {
						return a1[i];
					}
				}
			}
		} catch (NullPointerException e) {

		}
		return null;
	}

	public static Coupon getResultSetCoupon(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String typeget = resultSet.getString("category");
		String title = resultSet.getString("TITLE");
		String d1 = resultSet.getString("Start_date");
		String d2 = resultSet.getString("end_date");
		int amount = resultSet.getInt("amount");
		String Description = resultSet.getString("description");
		double price = resultSet.getDouble("price");
		String image = resultSet.getString("image");
		CouponType c = null;
		c = CouponType.getCategorie(typeget);
//		LocalDate d1 = LocalDate.parse(startDate);
//		LocalDate d2 = LocalDate.parse(endDate);
		long compid = resultSet.getLong("compID");
		return new Coupon(id, c, title, Description, d1, d2, amount, price, image, compid);
	}
	public static Company getResultSetComapny(ResultSet resultSet) throws SQLException, CouponSystemExeption {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("Name");
		String email = resultSet.getString("Email");
		CouponsDAO coupondao = new CouponsDAO();
		ArrayList<Coupon> coupons = coupondao.readCouponsByCompany(id);;

		return new Company(id, name, email, null, coupons);
	}
	public static Customer getResultSetCustomer(ResultSet resultSet) throws SQLException, CouponSystemExeption {
		long id = resultSet.getInt("id");
		String CustName = resultSet.getString("Custname");
		String email = resultSet.getString("email");
		CouponsDAO coupondao = new CouponsDAO();
		ArrayList<Coupon> coupons = coupondao.readCouponsByCustomer(id);
		return new Customer(id, CustName, email, null, coupons);
		
	}

	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	public static boolean CheckifAdminloginOnCookie(Cookie[] arrcookie) {
		if(arrcookie!=null) {
			for (Cookie cookie : arrcookie) {
				if(cookie.getName().equals("Adminlog")&&cookie.getValue().equals("9999")) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean CheckifComapnyloginOnCookie(Cookie[] arrcookie) {
		if(arrcookie!=null) {
			for (Cookie cookie : arrcookie) {
				if(cookie.getName().equals("Companylog")) {
					return true;
				}
			}
		}
		return false;
	}
	public static boolean CheckifCustomerloginOnCookie(Cookie[] arrcookie) {
		if(arrcookie!=null) {
			for (Cookie cookie : arrcookie) {
				if(cookie.getName().equals("Customerlog")) {
					return true;
				}
			}
		}
		return false;
	}

}
