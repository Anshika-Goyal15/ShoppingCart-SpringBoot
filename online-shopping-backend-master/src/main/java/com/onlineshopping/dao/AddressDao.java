package com.onlineshopping.dao;

import org.springframework.stereotype.Repository;
import com.onlineshopping.model.Address;

import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface AddressDao extends MongoRepository<Address, String> {

}
