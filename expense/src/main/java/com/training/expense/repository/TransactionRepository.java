package com.training.expense.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.Transaction;
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	

}
