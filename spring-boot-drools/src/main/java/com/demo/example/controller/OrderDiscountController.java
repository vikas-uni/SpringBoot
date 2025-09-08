package com.demo.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.example.model.OrderDiscount;
import com.demo.example.model.OrderRequest;
import com.demo.example.service.OrderDiscountService;

@RestController
public class OrderDiscountController {

	@Autowired
	private OrderDiscountService orderDiscountService;

	@PostMapping("/get-discount")
	public ResponseEntity<OrderDiscount> getDiscount(@RequestBody OrderRequest orderRequest) {
		OrderDiscount discount = orderDiscountService.getDiscount(orderRequest);
		return new ResponseEntity<OrderDiscount>(discount, HttpStatus.OK);
	}
}
