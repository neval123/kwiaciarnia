package com.example.application.views.history;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.data.entity.Flower;
import com.example.application.data.entity.Transaction;
import com.example.application.data.service.CustomerService;
import com.example.application.data.service.TransactionService;
import com.example.application.data.entity.Customer;
@PageTitle("History")
@Route(value = "history", layout = MainLayout.class)
public class HistoryView extends VerticalLayout {
	
	private final CustomerService customerService;
	private final TransactionService transactionService;
	private boolean created = false;
	
    public HistoryView(CustomerService customerService,TransactionService transactionService) {
    	this.customerService = customerService;
    	this.transactionService = transactionService;
        setSpacing(false);
        
        add(new H2("Choose a customer:"));
        Grid<Transaction> grid = new Grid<>(Transaction.class, false);
        ComboBox<Customer> combobox = new ComboBox("Customer");
        combobox.setItems(customerService.getCustomers());
        combobox.setItemLabelGenerator(Customer::getFullName);
        combobox.addValueChangeListener(e->{
        	if(!created) {
        		created = true;
        		add(new H2("Transactions:"));
        		grid.addColumn("transactionNumber").setAutoWidth(true);
        		grid.addColumn("positionNumber").setAutoWidth(true);
                grid.addColumn("flowerName").setAutoWidth(true);
                grid.addColumn("amount").setAutoWidth(true);
                grid.addColumn("price").setAutoWidth(true);
                grid.addColumn("totalPriceOfTransaction").setAutoWidth(true);
                grid.addColumn("time").setAutoWidth(true);
                
                grid.setItems(transactionService.getCustomerTransaction(e.getValue()));
                grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
                add(grid);
        	}
        	else {
        		grid.setItems(transactionService.getCustomerTransaction(e.getValue()));
        	}
        });
//        setSizeFull();
        add(combobox);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
