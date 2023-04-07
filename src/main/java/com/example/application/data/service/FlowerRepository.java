package com.example.application.data.service;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Flower;

public interface FlowerRepository  extends JpaRepository<Flower, Integer>{

	@Transactional
	@Modifying
	@Query("UPDATE Flower f SET f.amount = ?1 WHERE f.id = ?2")
	void setFlowerAmount(int amount, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Flower f SET f.lastDelivery = ?1 WHERE f.id = ?2")
	void setFlowerLastDelivery(LocalDate lastDelivery, int id);
	
	@Query("SELECT f.amount FROM Flower f WHERE f.id = ?1")
	int getFlowerAmount(int id);
}
