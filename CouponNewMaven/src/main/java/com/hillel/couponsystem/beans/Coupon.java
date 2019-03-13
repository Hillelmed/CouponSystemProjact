
package com.hillel.couponsystem.beans;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.hillel.couponsystem.enums.CouponType;
/**
 * @author HillelMed
 */
@XmlRootElement
@Component
public class Coupon implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int amount;
	private long id,compID;
	private CouponType type;
	private String title,description,image;
//	private LocalDate startDate,endDate;
	private String startDates,endDates;
	private double price;
	
	public Coupon()
	{
	}
	
/*  
 * CTor for coupon
 * @param id
 * @param category
 * @param title
 * @param description
 * @param startDate
 * @param endDate
 * @param amount
 * @param price
 * @param image
 * 
 */
	
//	public Coupon(long id,CouponType type,String title,String description,LocalDate startDate,LocalDate endDate,int amount,double price,String image,long compID)
//	{
//		this.id = id;
//		this.type=type;
//		this.amount = amount;
//		this.title = title;
//		this.description = description;
//		this.image = image;
//		this.price=price;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.compID=compID;
//	}
	/*
	 * this Ctos for Json all String/
	 */
	public Coupon(long id,CouponType type,String title,String description,String startDate,String endDate,int amount,double price,String image,long compID)
	{
		this.id = id;
		this.type=type;
		this.amount = amount;
		this.title = title;
		this.description = description;
		this.image = image;
		this.price=price;
		this.startDates = startDate;
		this.endDates = endDate;
		this.compID=compID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public CouponType getCategory() {
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
//	public LocalDate getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}
//	public LocalDate getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(LocalDate endDate) {
//		this.endDate = endDate;
//	}
	
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

	public long getCompID() {
		return compID;
	}

	public void setCompID(long compID) {
		this.compID = compID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

public CouponType getType() {
		return type;
	}

/**
 * Get Coupon by toString
 */



@Override
public String toString() {
	return "Coupon [amount=" + amount + ", id=" + id + ", compID=" + compID + ", type=" + type + ", title=" + title
			+ ", description=" + description + ", image=" + image + ", startDates=" + startDates + ", endDates="
			+ endDates + ", price=" + price + "]";
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (compID ^ (compID >>> 32));
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
	Coupon other = (Coupon) obj;
	if (compID != other.compID)
		return false;
	if (id != other.id)
		return false;
	return true;
}

    
    }
