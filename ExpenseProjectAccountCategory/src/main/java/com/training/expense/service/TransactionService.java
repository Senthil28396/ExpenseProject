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
		
		/*
		 * PaymentMode s=transaction.getPaymentMode(); //System.out.println(s); String
		 * l=s.getMode(); //double
		 * initial_amount=transaction.getPaymentMode().getInitial_amount(); long
		 * amount=transaction.getAmount(); String detail=transaction.getDetail();
		 * 
		 * PaymentMode list=(PaymentMode) paymentrepository.findByMode(l);
		 * System.out.println(list.getInitial_amount()); double
		 * initial_amount=list.getInitial_amount(); //transaction.getDate();
		 * 
		 * //PaymentMode p=new PaymentMode();
		 * 
		 * int current_balance=0; if(detail.equals("income")) { current_balance=(int)
		 * (amount+initial_amount); //list.setId(list.getId()); //list.setMode(l);
		 * list.setInitial_amount(current_balance); paymentrepository.save(list); } else
		 * if(detail.equals("expense")) { if(initial_amount>=amount) {
		 * current_balance=(int) (initial_amount-amount); //list.setId(list.getId());
		 * //list.setMode(l); list.setInitial_amount(current_balance);
		 * paymentrepository.save(list); } else {
		 * System.out.println("insufficient fund"); } }
		 */        
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

    // Assign the existing or newly created category to the transaction
    transaction.setCategory(category);

    // Retrieve existing payment mode from the database or create a new one if necessary
    PaymentMode paymentMode = paymentrepository.findByMode(transaction.getPaymentMode().getMode())
            .orElseGet(() -> paymentrepository.save(transaction.getPaymentMode()));

    // Assign the existing or newly created payment mode to the transaction
    transaction.setPaymentMode(paymentMode);

    // Save the transaction
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

		    // Assign the existing or newly created category to the transaction
		    transaction.setCategory(category);

		    // Retrieve existing payment mode from the database or create a new one if necessary
		    PaymentMode paymentMode = paymentrepository.findByMode(transaction.getPaymentMode().getMode())
		            .orElseGet(() -> paymentrepository.save(transaction.getPaymentMode()));

		    // Assign the existing or newly created payment mode to the transaction
		    transaction.setPaymentMode(paymentMode);

		    transactionDetail.setCategory(transaction.getCategory());
			  transactionDetail.setPaymentMode(transaction.getPaymentMode());

			transactionrepository.save(transactionDetail);

		    // Save the transaction
		    //transactionrepository.save(transaction);

		}
		else
		{
            throw new TransactionNotFoundException("Transaction id not exist"+id);
		}
	}

	public void deleteTransaction(int id) {
		Optional<Transaction> t=transactionrepository.findById(id);
		if(t.isPresent())
		{
//			Transaction trans=t.get();
//			trans.getAmount()
			transactionrepository.deleteById(id);
		}
		else
		{
			throw new TransactionNotFoundException("Transaction id not exist"+id);
		}
	}
}
