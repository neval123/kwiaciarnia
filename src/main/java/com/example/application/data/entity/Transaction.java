package com.example.application.data.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@IdClass(TransactionId.class)
public class Transaction {
	
	@Id
	private int transactionNumber;		
	@Id
	private int positionNumber;
	private String flowerName;
	private int amount;
	private int price;
	@Column(nullable = true)
	private int totalPriceOfTransaction;
	
	
	@UpdateTimestamp
	private Timestamp time;
	
	@ManyToOne
	private Customer customer;
	@OneToOne
	private Flower flower;
	
	public int getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public int getPositionNumber() {
		return positionNumber;
	}
	public void setPositionNumber(int positionNumber) {
		this.positionNumber = positionNumber;
	}
	public String getFlowerName() {
		return flowerName;
	}
	public void setFlowerName(String flowerName) {
		this.flowerName = flowerName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotalPriceOfTransaction() {
		return totalPriceOfTransaction;
	}
	public void setTotalPriceOfTransaction(int totalPriceOfTransaction) {
		this.totalPriceOfTransaction = totalPriceOfTransaction;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Flower getFlower() {
		return flower;
	}
	public void setFlower(Flower flower) {
		this.flower = flower;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
