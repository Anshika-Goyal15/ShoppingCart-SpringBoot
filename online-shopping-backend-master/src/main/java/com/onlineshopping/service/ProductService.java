package com.onlineshopping.service;

import org.springframework.web.multipart.MultipartFile;

import com.onlineshopping.model.Product;

public interface ProductService {
	
	void addProduct(Product product, MultipartFile productImmage);

	Product getProductById(String productId);

	void deleteProduct(String productId);

	void updateProduct(Product existingProduct);

	void updateProduct(Product existingProduct, Object image);

	
}
