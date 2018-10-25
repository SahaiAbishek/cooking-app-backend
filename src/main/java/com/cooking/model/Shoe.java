package com.cooking.model;

import java.io.Serializable;
import java.util.Date;

public class Shoe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String brand;

	private String model;

	private Date startDate;

	private Date endDate;

	private Long miles;

	private Long userId;

	private byte[] pic;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getMiles() {
		return miles;
	}

	public void setMiles(Long miles) {
		this.miles = miles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}
