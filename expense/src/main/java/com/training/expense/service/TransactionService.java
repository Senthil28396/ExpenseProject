package com.training.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.training.expense.model.Transaction;
import com.training.expense.repository.TransactionRepository;

public class TransactionService {
	
@Autowired
TransactionRepository transactionrepository;

	public void addTransaction(Transaction transaction) {
		transactionrepository.save(transaction);
		
	}

	public List<Transaction> getCategory() {
	
		return null;
	}

}
