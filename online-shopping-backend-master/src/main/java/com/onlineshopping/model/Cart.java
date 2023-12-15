package com.onlineshopping.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value="cart")
public class Cart {
	
	@Id
	private String id;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private int quantity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
