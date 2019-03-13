
package com.hillel.couponsystem.beans;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
/**
 * Company
 * @author HillelMed
 */
@XmlRootElement
@Component
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * company ID,name,email, password and List of coupons
	 */
	private long id;
	private String name,email,password;
	private ArrayList<Coupon> coupons;
	
	public Company()
	{
	}
	
	/**
	 * Ctor for Company
	 * @param id- company ID
	 * @param name
	 * @param email
	 * @param password
	 * @param coupons
	 */

	public Company(long id, String name, String email, String password, ArrayList<Coupon> coupons) {
		setId(id);
		setName(name);
		setEmail(email);
		setPassword(password);
		setCoupons(coupons);
	}
	/**
	 * get ID
	 * @return company's ID
	 */
	public long getId() {
		return id;
	}
	/**
	 * set ID
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	/**
	 * Get all company's details by toString
	 */
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ",\ncoupons="
				+ getCoupons() + "] \n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Company other = (Company) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
