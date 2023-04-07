package com.example.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Flower;
import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Transaction;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
