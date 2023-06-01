package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.expense.model.Category;
import com.training.expense.model.PaymentMode;
import com.training.expense.model.Transaction;
import com.training.expense.model.TransactionNotFoundException;
import com.training.expense.repository.CategoryRepository;
import com.training.expense.repository.PaymentModeRepository;
import com.training.expense.repository.TransactionRepository;


@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionrepository;

	@Autowired
	PaymentModeRepository paymentModeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;


	public void addTransaction(Transaction transaction) {
	    if (transaction.getDetail().equalsIgnoreCase("expense")) {
	        Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
	        if (type.isPresent()) {
	            PaymentMode paymentMode = type.get();
	            if (paymentMode.getInitial_amount() >= transaction.getAmount()) {
	                paymentMode.setInitial_amount(paymentMode.getInitial_amount() - transaction.getAmount());
	                paymentModeRepository.save(paymentMode);
	            } else {
	                throw new TransactionNotFoundException("Insufficient balance");
	            }
	        }
	    } else {
	        Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
	        if (type.isPresent()) {
	            PaymentMode paymentMode = type.get();
	            paymentMode.setInitial_amount(paymentMode.getInitial_amount() + transaction.getAmount());
	            paymentModeRepository.save(paymentMode);
	        }
	    }

	    // Retrieve existing category from the database or create a new one if necessary
	    Category category = categoryRepository.findByName(transaction.getCategory().getName())
	            .orElseGet(() -> categoryRepository.save(transaction.getCategory()));

	    // Assign the existing or newly created category to the transaction
	    transaction.setCategory(category);

	    // Retrieve existing payment mode from the database or create a new one if necessary
	    PaymentMode paymentMode = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode())
	            .orElseGet(() -> paymentModeRepository.save(transaction.getPaymentMode()));

	    // Assign the existing or newly created payment mode to the transaction
	    transaction.setPaymentMode(paymentMode);

	    // Save the transaction
	    transactionrepository.save(transaction);
	}



	public List<Transaction> getTransaction() {
		List<Transaction> transactions=(List<Transaction>) transactionrepository.findAll();
		return transactions;
		
	}

}
