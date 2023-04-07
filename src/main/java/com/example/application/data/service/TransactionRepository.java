package com.example.application.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query("SELECT t FROM Transaction t WHERE t.customer = ?1")
	List<Transaction> getCustomerTransaction(Customer customer);
	
	@Transactional
	@Modifying
	@Query("UPDATE Transaction t SET t.totalPriceOfTransaction = ?1 WHERE t.transactionNumber = ?2")
	void updateTotalPrice(int totalPrice, int transactionNumber);
}
