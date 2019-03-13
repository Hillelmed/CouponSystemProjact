/**
 * @author HillelMed
 */
package com.hillel.couponsystem.beans;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
@SuppressWarnings("serial")
@XmlRootElement
@Component
public class Customer implements Serializable {
	
	private long id;
	private String CustName,email,password;
	private ArrayList<Coupon> coupons;
	
	public Customer()
	{
	}
	/**
	 * Ctor for Customer
	 * @param id
	 * @param Customer name
	 * @param email
	 * @param password
	 * @param coupons
	 */
	public Customer(long id, String CustNAME, String email, String password,
			ArrayList<Coupon> coupons) {
		this.id = id;
		this.CustName = CustNAME;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String CustName) {
		this.CustName = CustName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
			this.password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		if(coupons!=null) {
		for (int i = 0; i < coupons.size(); i++) {
			coupons.get(i).setAmount(1);
		}
		return coupons;
	}else {
		return null;
	}
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	/**
	 * get Customer details by toString
	 */
	@Override
	public String toString() {
		return "Customer [id=" + getId() + ", CustName=" + getCustName() + ", email=" + getEmail() + ", password=" + getPassword()
				+ ", coupons=" + getCoupons() + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	
}
