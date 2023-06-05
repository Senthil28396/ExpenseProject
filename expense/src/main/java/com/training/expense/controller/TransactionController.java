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
		List<Transaction> transaction=(List<Transaction>)trasactionService.getTransaction();
		return new ResponseEntity <List<Transaction>>(transaction,new HttpHeaders(),HttpStatus.OK);
	}
	@PutMapping
	public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
		trasactionService.updateTransaction(transaction);
		return new ResponseEntity<>(new HttpHeaders(),HttpStatus.OK);
	}
	 @DeleteMapping("/{id}") 
	  public HttpStatus deleteTransaction(@PathVariable int id) 
	  {
		  trasactionService.deleteTransaction(id);
		  return HttpStatus.OK;
	  }
}


