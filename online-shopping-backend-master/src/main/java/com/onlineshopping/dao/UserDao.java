package com.onlineshopping.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.onlineshopping.model.User;

@Repository
public interface UserDao extends MongoRepository<User, String> {
	
	User findByEmailIdAndPasswordAndRole(String emailId, String password, String role);
	List<User> findByRole(String role);
	Optional<User> findById(String userId);

}
