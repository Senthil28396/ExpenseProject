package com.training.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.PaymentMode;
import com.training.expense.model.Transaction;
@Repository
public interface PaymentModeRepository extends CrudRepository<PaymentMode, Integer> {

	

	void save(Transaction newExpense);
	
	Optional<PaymentMode> findByMode(String mode);

	//void update(PaymentMode newExpense);

	//Optional<PaymentMode> findByMode(PaymentMode paymentMode);

}
