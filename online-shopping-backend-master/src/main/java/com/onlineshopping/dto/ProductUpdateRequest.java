package com.onlineshopping.dto;

import java.math.BigDecimal;

public class ProductUpdateRequest {
	
	private String title;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Object image;

    // getters and setters

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

	@Override
	public String toString() {
		return "ProductUpdateRequest [title=" + title + ", description=" + description + ", price=" + price
				+ ", quantity=" + quantity + ", image=" + image + ", getTitle()=" + getTitle() + ", getDescription()="
				+ getDescription() + ", getPrice()=" + getPrice() + ", getQuantity()=" + getQuantity() + ", getImage()="
				+ getImage() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
    

}