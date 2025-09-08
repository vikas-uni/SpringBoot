package com.demo.example;

import com.demo.example.config.DroolsConfig;
import com.demo.example.model.CustomerType;
import com.demo.example.model.OrderDiscount;
import com.demo.example.model.OrderRequest;
import com.demo.example.service.OrderDiscountService;

public class TestApp {
	
	public static void main(String[] args) {
		
		OrderDiscountService orderDiscountService = new OrderDiscountService();
		orderDiscountService.setKieContainer(new DroolsConfig().kieContainer());
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setAge(10);
		orderRequest.setCustomerNumber("20");
		orderRequest.setAmount(1400);
		orderRequest.setCustomerType(CustomerType.NEW);
		
		OrderDiscount dcount = orderDiscountService.getDiscount(orderRequest);
		
		System.out.println(dcount.getDiscount());
	}

}
