package com.onlineshopping.dto;

public class AddReviewRequest {
	
	private String userId;
	
	private String productId;
	
	private int star;
	
	private String review;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "AddReviewRequest [userId=" + userId + ", productId=" + productId + ", star=" + star + ", review="
				+ review + "]";
	}
	
}
