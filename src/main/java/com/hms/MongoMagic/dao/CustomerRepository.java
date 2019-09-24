package com.hms.MongoMagic.dao;

import java.util.List;
import com.hms.MongoMagic.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    @SuppressWarnings("unchecked")
	public Customer save(Customer customer);
    

}