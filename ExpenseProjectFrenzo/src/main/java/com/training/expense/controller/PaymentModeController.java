package com.training.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.expense.model.PaymentMode;
import com.training.expense.model.PaymentModeNotFoundException;
import com.training.expense.service.PaymentModeService;


@RestController
@RequestMapping("/paymentmode")
public class PaymentModeController {
	@Autowired 
	PaymentModeService paymentModeService;
	
	@PostMapping
	public String addAccount(@RequestBody PaymentMode acconut) throws Exception {
		
		paymentModeService.addAccount(acconut);
		return "created";
	}
	
	@GetMapping
	public ResponseEntity<List<PaymentMode>> getAccount(){
		List<PaymentMode> accounts=paymentModeService.getAccount();
		return new ResponseEntity<List<PaymentMode>>(accounts,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public HttpStatus updateAccount(@RequestBody PaymentMode account,@PathVariable int id) {
		paymentModeService.updateAccount(account,id);
		return HttpStatus.OK;
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteAccount(@PathVariable int id) throws PaymentModeNotFoundException {
		paymentModeService.deleteAccount(id);
		return HttpStatus.OK;
	}
	
}
