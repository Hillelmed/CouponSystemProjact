package com.hillel.couponsystem.entitiy;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class CustomerEntity {
	
	@GeneratedValue
	@Id
	private long id;
	
	@Column(name="Name", nullable=false)
	private String name;
	
	@Column(name="Email", nullable=false)
	private String email;
	
	@Column(name="Password", nullable=false)
	private long password;
	
	@ManyToMany(cascade = {CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinTable(name="customer_coupon", joinColumns= 
   {@JoinColumn(name="cust_id")},inverseJoinColumns={@JoinColumn(name="coup_id")})
	private Collection<CouponEntity> coupons;

	
	
	public CustomerEntity(String name, String email, long password, List<CouponEntity> arrcoupons) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = arrcoupons;
	}

	public CustomerEntity() {
	}

	public long getId() {
		return id;
	}

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

	public long getPassword() {
		return password;
	}

	public void setPassword(long password) {
		this.password = password;
	}

	public Collection<CouponEntity> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<CouponEntity> arrcoupons) {
		this.coupons = arrcoupons;
	}

	@Override
	public String toString() {
		return "CustomerEntity [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", coupons=" + coupons + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (password ^ (password >>> 32));
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
		CustomerEntity other = (CustomerEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (password != other.password)
			return false;
		return true;
	}
	
	
	

	
	

}
