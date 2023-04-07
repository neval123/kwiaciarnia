package com.example.application.views.buyflowers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Flower;
import com.example.application.data.entity.Transaction;
import com.example.application.data.service.CustomerService;
import com.example.application.data.service.FlowerService;
import com.example.application.data.service.TransactionService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Buy flowers")
@Route(value = "buy", layout = MainLayout.class)
public class BuyflowersView extends VerticalLayout {

	private final CustomerService customerService;
	private final FlowerService flowerService;
	private final TransactionService transactionService;
	private boolean createdC = false;
	private boolean createdF = false;
	private TextField flowerName;
	private TextField flowerPrice;
	private NumberField flowerAmount;
	private Button buyFlower;
	private H2 amountInfo;
	private Button addToCart;
	private List<Flower> flowersToBuy = new ArrayList();
	private List<Integer> amounts = new ArrayList();
	private List<Transaction> transactions = new ArrayList();
	private int totalPrice = 0;
	private int it = 0;
	private int currentPrimary = 1;
	private int positions;
	private int position;
	private boolean checked = false;
    public BuyflowersView(CustomerService customerService, FlowerService flowerService,
    		TransactionService transactionService) {
        this.flowerService = flowerService;
        this.customerService = customerService;
        this.transactionService = transactionService;
		setSpacing(false);
		positions = 0;
		transactions = transactionService.getTransactions();
		Transaction tr = transactions.stream().max(Comparator.comparing(Transaction::getTransactionNumber)).orElse(null);
		if(!(tr == null)) {
			currentPrimary = tr.getTransactionNumber()+1;
			checked = true;
		}
		buyFlower = new Button("Buy");
		buyFlower.setEnabled(false);
		addToCart = new Button("Add");
		ComboBox<Customer> comboboxC = new ComboBox("Customer");
        comboboxC.setItems(customerService.getCustomers());
        comboboxC.setItemLabelGenerator(Customer::getFullName);
        ComboBox<Flower> comboboxF = new ComboBox("Flower");
        comboboxC.addValueChangeListener(e->{
        	if(!createdC) {
        		createdC = true;
        		add(new H2("Choose a flower:"));
        		comboboxF.setItems(flowerService.getFlowers());
        		comboboxF.setItemLabelGenerator(Flower::getName);
        		add(comboboxF);
        	}
        });
        comboboxF.addValueChangeListener(e->{
        	Flower f = e.getValue();
        	
        	if(!createdF) {
//        		buyFlower.setEnabled(true);
        		amountInfo = new H2();
        		createdF = true;
        		flowerName = new TextField("Name");
        		flowerAmount = new NumberField("Amount");
        		flowerPrice = new TextField("Price");      
        		flowerName.setValue(f.getName());
        		flowerPrice.setValue(Integer.toString(f.getPrice()));
        		flowerPrice.setEnabled(false);
        		flowerName.setEnabled(false);
        		add(amountInfo, flowerName, flowerPrice, flowerAmount, addToCart, buyFlower);        		
        	}
        	if(f.getAmount()>0) {
        		amountInfo.setText("Currently available (" + f.getAmount() + ")");
        	}else {
        		amountInfo.setText("Out of stock");
        	}
        });
        add(new H2("Choose a customer:"), comboboxC);
       
        addToCart.addClickListener(e->{
        	int amount = (int)Math.round(flowerAmount.getValue());
        	Flower f = comboboxF.getValue();
        	if(flowerName.getValue()!=""&&flowerPrice.getValue()!=""
        			&&amount>0&&amount<=flowerService.getFlowerAmount(f.getId())) {
        		buyFlower.setEnabled(true);
        		flowersToBuy.add(f);
        		amounts.add(amount);
        		positions = positions+1;
        		Notification not = Notification.show("Position added! Current cart items: " + positions);
        //		int newAmount = flowerService.getFlowerAmount(f.getId());
        		double newAmount = flowerService.getFlowerAmount(f.getId()) - flowerAmount.getValue();
        		flowerService.setFlowerAmount((int)newAmount, f.getId());
        		if(newAmount > 0) {
        			amountInfo.setText("Currently available (" + flowerService.getFlowerAmount(f.getId()) + ")");
        		}else {
        			amountInfo.setText("Out of stock!");
        		}
        		flowerAmount.setValue(0d);
        	}else {
        		Notification not = Notification.show("The amount is incorrect, try again");
        		flowerAmount.setValue(0d);
        	}
        });
        
        buyFlower.addClickListener(e->{       
        	if(!flowersToBuy.isEmpty()) {      		
      /*  		flowersToBuy.stream().forEach(x->{
        			totalPrice = totalPrice + amounts.get(it) * x.getPrice();
        			it+=1;
        		});*/
        		it = 0;
        		position = 1;
        		flowersToBuy.forEach(x->{
        			Transaction transaction = new Transaction();
        			transaction.setAmount(amounts.get(it));
        			transaction.setCustomer(comboboxC.getValue());
        			transaction.setFlower(x);
        			transaction.setFlowerName(x.getName());
        			transaction.setPrice(x.getPrice()*amounts.get(it));  
        			transaction.setPositionNumber(position);
        			
        			transaction.setTransactionNumber(currentPrimary);
        			
        			totalPrice = totalPrice + amounts.get(it) * x.getPrice();
        			it+=1;
        			
  
        			transactionService.addTransaction(transaction);
        			position += 1;
        		});
        		flowersToBuy.forEach(x->{
        			transactionService.updateTotalPrice(totalPrice, currentPrimary);
        		});
        		currentPrimary += 1;
        		position = 1;
 //       		flowersToBuy.stream().forEach(z->{
  //      			flowerService.setFlowerAmount(z.getAmount() - amounts.get(it), z.getId());
  //      			it+=1;
  //      		});
 //       		flowerService.setFlowerAmount(f.getAmount() - amount, f.getId());
        		Notification not = Notification.show("Total cost: " + totalPrice);
        		it = 0;
        		totalPrice = 0;
        		flowersToBuy.clear();
        		amounts.clear();      
        		positions = 0;
        	}
        });
//        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
