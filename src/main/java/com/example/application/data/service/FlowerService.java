package com.example.application.data.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Flower;

@Service
public class FlowerService {
	
	private final FlowerRepository repository;

    @Autowired
    public FlowerService(FlowerRepository repository) {
        this.repository = repository;
    }
    
    public List<Flower> getFlowers(){
    	return repository.findAll();
    }
    public void setFlowerAmount(int amount, int id) {
    	repository.setFlowerAmount(amount, id);
    }
    public void setFlowerLastDelivery(LocalDate lastDelivery, int id) {
    	repository.setFlowerLastDelivery(lastDelivery, id);
    }
    public int getFlowerAmount(int id) {
    	return repository.getFlowerAmount(id);
    }
}
