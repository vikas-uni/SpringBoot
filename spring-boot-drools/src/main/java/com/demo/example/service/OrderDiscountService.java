package com.demo.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.example.model.OrderDiscount;
import com.demo.example.model.OrderRequest;

@Service
public class OrderDiscountService {

	@Autowired
	private KieContainer kieContainer;

	public OrderDiscount getDiscount(OrderRequest orderRequest) {
		OrderDiscount orderDiscount = new OrderDiscount();
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.setGlobal("orderDiscount", orderDiscount);
		kieSession.insert(orderRequest);
		kieSession.fireAllRules();
		kieSession.dispose();
		return orderDiscount;
	}

	public KieContainer getKieContainer() {
		return kieContainer;
	}

	public void setKieContainer(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}
	
}