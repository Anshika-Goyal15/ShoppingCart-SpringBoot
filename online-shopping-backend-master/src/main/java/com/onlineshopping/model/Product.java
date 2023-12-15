package com.onlineshopping.model;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Document(value="product")
public class Product {
	
	@Id
	private String id;
    private String title;
	private String description;
	private int quantity;
    private BigDecimal price;
    private String imageName;
    
    @ManyToOne
    @JoinColumn(name ="categoryId")
    private Category category;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
