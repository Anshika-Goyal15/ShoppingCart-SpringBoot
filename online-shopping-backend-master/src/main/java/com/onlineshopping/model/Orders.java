package com.onlineshopping.model;


import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Document(value="orders")
public class Orders {
	
	@Id
	private String id;
	
	private String orderId;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private int quantity;
	
	private String orderDate;  // 13-01-2022 10:00 PM
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId2) {
		this.orderId = orderId2;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
}
