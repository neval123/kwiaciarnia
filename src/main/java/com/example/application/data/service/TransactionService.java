package com.example.application.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.data.entity.*;
@Service
public class TransactionService {
	
	private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }
    public List<Transaction> getTransactions(){
    	return repository.findAll();
    }
    public List<Transaction> getCustomerTransaction(Customer customer){
    	return repository.getCustomerTransaction(customer);
    }
    public void addTransaction(Transaction transaction) {
    	repository.save(transaction);
    }
    public void updateTotalPrice(int totalPrice, int transactionNumber) {
    	repository.updateTotalPrice(totalPrice, transactionNumber);
    }
}
