package com.onlineshopping.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.onlineshopping.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface ProductDao extends MongoRepository<Product, String> {
	
	@Query("{'categoryId': ?0}")
	List<Product> findByCategoryId(String categoryId);

	Optional<Product> findById(String productId);


}

