package com.onlineshopping.dao;


import com.onlineshopping.model.Category;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface CategoryDao extends MongoRepository<Category, String> {

	Optional<Category> findById(String categoryId);
	

}
