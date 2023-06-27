package com.training.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
