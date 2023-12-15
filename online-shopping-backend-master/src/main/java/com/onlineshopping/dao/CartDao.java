package com.onlineshopping.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.onlineshopping.model.Cart;

import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface CartDao extends MongoRepository<Cart, String> {	
	
	List<Cart> findByUser_id(String userId);

}
