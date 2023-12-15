package com.onlineshopping.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.onlineshopping.model.Orders;
import com.onlineshopping.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface OrderDao extends MongoRepository<Orders, String> {
	
	List<Orders> findByOrderId(String orderId);
	List<Orders> findByUser_idAndProduct_id(int userId, int productId);
	List<Orders> findByUser(User user);
	List<Orders> findByUser_id(String userId);
	
}
