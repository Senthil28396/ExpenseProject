package com.training.expense.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.expense.model.Category;
import com.training.expense.model.Transaction;
import com.training.expense.service.TransactionService;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService trasactionService;
	
	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
		trasactionService.addTransaction(transaction);
		return new ResponseEntity<>(new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity <List<Transaction>> getTransaction() {
		List<Transaction> transactions=trasactionService.getTransaction();
		return new ResponseEntity <List<Transaction>>(transactions,new HttpHeaders(),HttpStatus.OK);
	}
}


