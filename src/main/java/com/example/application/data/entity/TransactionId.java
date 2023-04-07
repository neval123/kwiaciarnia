package com.example.application.data.entity;

import java.io.Serializable;

public class TransactionId implements Serializable{

	private int transactionNumber;
	private int positionNumber;
	
	public TransactionId(int transactionNumber, int positionNumber) {
		this.transactionNumber = transactionNumber;
		this.positionNumber = positionNumber;
	}
	public TransactionId() {}
}
