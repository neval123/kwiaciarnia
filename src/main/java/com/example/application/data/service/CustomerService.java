package com.example.application.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Customer;

@Service
public class CustomerService {
		
	private final CustomerRepository repository;

	@Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
    
    public Customer addCustomer(Customer customer) {
    	return repository.save(customer);
    }
    
    public List<Customer> getCustomers(){
    	return repository.findAll();
    }
    
   
}
