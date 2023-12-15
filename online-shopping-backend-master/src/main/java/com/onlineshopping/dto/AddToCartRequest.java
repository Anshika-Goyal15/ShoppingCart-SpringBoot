package com.onlineshopping.dto;

public class AddToCartRequest {
	
	private String cartId;
	
	private String productId;
	
	private int quantity;
	
	private String userId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AddToCartRequest [productId=" + productId + ", quantity=" + quantity + ", userId=" + userId + "]";
	}

	public String getCartId() {
		return cartId;
	}

	public void setOrderId(String cartId) {
		this.cartId = cartId;
	}

	
	
	
	
}
