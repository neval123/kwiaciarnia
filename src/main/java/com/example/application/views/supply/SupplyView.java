package com.example.application.views.supply;

import java.time.LocalDate;

import com.example.application.data.entity.Flower;
import com.example.application.data.service.CustomerService;
import com.example.application.data.service.FlowerService;
import com.example.application.data.service.TransactionService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

@PageTitle("Supply")
@Route(value = "supply", layout = MainLayout.class)
public class SupplyView extends VerticalLayout{
	
	private final CustomerService customerService;
	private final FlowerService flowerService;
	private final TransactionService transactionService;
	private boolean created = false;
	private H2 amountInfo;
	private NumberField amount;
	private Button supply;
	
	public SupplyView(CustomerService customerService, FlowerService flowerService,
    		TransactionService transactionService) {
        this.flowerService = flowerService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        
        supply = new Button("Supply");
        amount = new NumberField("Amount");
        amountInfo = new H2();
        supply.setEnabled(false);
        add(new H2("Select a flower:"));
        ComboBox<Flower> comboboxF = new ComboBox("Flower");
        comboboxF.setItems(flowerService.getFlowers());
        comboboxF.setItemLabelGenerator(Flower::getName);
        comboboxF.addValueChangeListener(e->{
        	Flower f = e.getValue();
        	amountInfo.setText("Amount currently in stock: " + f.getAmount());
        	if(!created) {
        		created = true;
        		add(amountInfo, amount, supply);
        		supply.setEnabled(true);
        	}
        });
        supply.addClickListener(e->{
        	Flower f = comboboxF.getValue();
        	int amountToSupply = (int) Math.round(amount.getValue());
        	if(amountToSupply > 0) {
        		int oldAmount = flowerService.getFlowerAmount(f.getId());
        		int newAmount = oldAmount + amountToSupply;
        		flowerService.setFlowerAmount(newAmount, f.getId());
        		LocalDate latestDelivery = LocalDate.now();
        		flowerService.setFlowerLastDelivery(latestDelivery, f.getId());
        		amountInfo.setText("Amount currently in stock: " + newAmount);
        		amount.setValue(0d);
        	}else {
        		Notification not = Notification.show("Enter a valid amount!");
        	}
        });
        add(comboboxF);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}
}
