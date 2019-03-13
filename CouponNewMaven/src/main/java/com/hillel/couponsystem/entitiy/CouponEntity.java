package com.hillel.couponsystem.entitiy;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hillel.couponsystem.enums.CouponType;

@Entity
@Table(name="coupons")
public class CouponEntity {
	
	@GeneratedValue
	@Id
	private long id;
	
	@Column(name="amount", nullable=false)
	private int amount;
	@Column(name="category", nullable=false)
	private CouponType type;
	@Column(name="title", nullable=false)
	private String title;
	@Column(name="description", nullable=false)
	private String description;
	@Column(name="image", nullable=false)
	private String image;
	@Column(name="Start_date", nullable=false)
	private String startDates;
	@Column(name="end_date", nullable=false)
	private String endDates;
	@Column(name="price", nullable=false)
	private double price;
	
	@ManyToOne
	private CompanyEntity company;
	
	@ManyToMany(mappedBy="coupons",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<CustomerEntity> customers;
	

	public Collection<CustomerEntity> getCustomers() {
		return customers;
	}
	public void setCustomers(Collection<CustomerEntity> customers) {
		this.customers = customers;
	}
	public CompanyEntity getCompany() {
		return company;
	}
	public void setCompany(CompanyEntity company) {
		this.company = company;
	}
	public CouponEntity() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStartDates() {
		return startDates;
	}

	public void setStartDates(String startDates) {
		this.startDates = startDates;
	}

	public String getEndDates() {
		return endDates;
	}

	public void setEndDates(String endDates) {
		this.endDates = endDates;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}



	@Override
	public String toString() {
		return "CouponEntitiy [id=" + id + ", amount=" + amount + ", type=" + type + ", title=" + title
				+ ", description=" + description + ", image=" + image + ", startDates=" + startDates + ", endDates="
				+ endDates + ", price=" + price + ", company=" + company.getId() + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CouponEntity other = (CouponEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	

	

}
