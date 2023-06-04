package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
PaymentModeRepository paymentrepository;

@Autowired
CategoryRepository categoryrepository;

	public void addTransaction(Transaction transaction) {
		  
		if (transaction.getDetail().equalsIgnoreCase("expense")) {
	        Optional<PaymentMode> type = paymentrepository.findByMode(transaction.getPaymentMode().getMode());
	        if (type.isPresent()) {
	            PaymentMode paymentMode = type.get();
	            if (paymentMode.getInitial_amount() >= transaction.getAmount()) {
	                paymentMode.setInitial_amount(paymentMode.getInitial_amount() - transaction.getAmount());
	                paymentrepository.save(paymentMode);
	            } 
	            else 
	            {
	                System.out.println("Insufficient fund");
	            }
	        }
	    } else {
	        Optional<PaymentMode> type = paymentrepository.findByMode(transaction.getPaymentMode().getMode());
	        if (type.isPresent()) {
	            PaymentMode paymentMode = type.get();
	            paymentMode.setInitial_amount(paymentMode.getInitial_amount() + transaction.getAmount());
	            paymentrepository.save(paymentMode);
	        }
	    }

	Category category = categoryrepository.findByName(transaction.getCategory().getName())
			.orElseGet(() -> categoryrepository.save(transaction.getCategory()));

    transaction.setCategory(category);

    PaymentMode paymentMode = paymentrepository.findByMode(transaction.getPaymentMode().getMode())
            .orElseGet(() -> paymentrepository.save(transaction.getPaymentMode()));

    transaction.setPaymentMode(paymentMode);

    transactionrepository.save(transaction);
}

	public List<Transaction> getTransaction() {
		List<Transaction> transaction=(List<Transaction>) transactionrepository.findAll();
		return transaction;
	}
	
	public void updateTransaction(Transaction transaction,int id)
	{
		Optional<Transaction> t=transactionrepository.findById(id);
		if(t.isPresent())
		{
			Transaction transactionDetail=t.get();
			transactionDetail.setDescription(transaction.getDescription());
			transactionDetail.setAmount(transaction.getAmount());
			transactionDetail.setDetail(transaction.getDetail());
			 

			Category category = categoryrepository.findByName(transaction.getCategory().getName())
					.orElseGet(() -> categoryrepository.save(transaction.getCategory()));

		    transaction.setCategory(category);

		    PaymentMode paymentMode = paymentrepository.findByMode(transaction.getPaymentMode().getMode())
		            .orElseGet(() -> paymentrepository.save(transaction.getPaymentMode()));

		    		    transaction.setPaymentMode(paymentMode);

		    transactionDetail.setCategory(transaction.getCategory());
			  transactionDetail.setPaymentMode(transaction.getPaymentMode());

			transactionrepository.save(transactionDetail);

		}
		else
		{
            throw new TransactionNotFoundException("Transaction id not exist"+id);
		}
	}

	public void deleteTransaction(int id) {
		Optional<Transaction> transaction=transactionrepository.findById(id);
		if(transaction.isPresent())
		{
			Transaction trans=transaction.get();
			long amount=trans.getAmount();
			String detail=trans.getDetail();
			PaymentMode mode=trans.getPaymentMode();
			double oldBalance=mode.getInitial_amount();
			if(detail.equals("expense"))
			{
				double initial_amount=oldBalance+amount;
				mode.setInitial_amount(initial_amount);
				paymentrepository.save(mode);
			}
			else
			{
				double initial_amount=oldBalance-amount;
				mode.setInitial_amount(initial_amount);
				paymentrepository.save(mode);
			}
			transactionrepository.deleteById(id);
		}
		else
		{
			throw new TransactionNotFoundException("Transaction id not exist"+id);
		}
	}
}
