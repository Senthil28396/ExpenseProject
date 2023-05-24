package com.training.expense.repository;

import org.springframework.data.repository.CrudRepository;

import com.training.expense.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	

}
