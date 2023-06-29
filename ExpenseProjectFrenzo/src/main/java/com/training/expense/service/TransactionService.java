package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
		Optional<Category> category = categoryRepository.findByName(transaction.getCategory().getName());
		PaymentMode paymentMode = type.get();
		Category cat = category.get();
		if (transaction.getDetail().equalsIgnoreCase("expense")) {
			if (type.isPresent() && category.isPresent()) {
				if (paymentMode.getInitial_amount() >= transaction.getAmount()) {
					paymentMode.setInitial_amount(paymentMode.getInitial_amount() - transaction.getAmount());
				} else {
					throw new TransactionNotFoundException("Insufficient balance");
				}
			} else {
				throw new TransactionNotFoundException("cannot found Paymenttype or category");
			}
		} else {
			if (type.isPresent() && category.isPresent()) {
				paymentMode.setInitial_amount(paymentMode.getInitial_amount() + transaction.getAmount());
			} else {
				throw new TransactionNotFoundException("cannot found Paymenttype or category");
			}
		}

		transaction.setCategory(cat);

		transaction.setPaymentMode(paymentMode);

		transactionrepository.save(transaction);
	}

	public List<Transaction> getTransaction() {
		List<Transaction> transaction = (List<Transaction>) transactionrepository.findAll();
		return transaction;
	}

	public void updateTransaction(Transaction transaction, int id) {
		Optional<Transaction> t = transactionrepository.findById(id);
		Transaction trans = t.get();
		if (transaction.getDetail().equalsIgnoreCase("expense")) {
			if(transaction.getDetail().equalsIgnoreCase(trans.getDetail())) {
			double amount = 0.0d;
			double newAmount = transaction.getAmount();
			double oldAmount = trans.getAmount();
			if (newAmount > oldAmount) {
				amount = newAmount - oldAmount;
			} 
			else {
				amount = oldAmount - newAmount;
			}
			Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
			if (type.isPresent()) {
				PaymentMode paymentMode = type.get();
				if (paymentMode.getInitial_amount() >= transaction.getAmount()) {
					paymentMode.setInitial_amount(paymentMode.getInitial_amount() - amount);
					paymentModeRepository.save(paymentMode);
				} else {
					System.out.println("Insufficient fund");
				}
			}
			}
			else if(!trans.getDetail().equalsIgnoreCase(transaction.getDetail())){
				double t1=trans.getAmount();
				double t2=transaction.getAmount();
				if(trans.getDetail().equalsIgnoreCase("income"))
				{
					Optional<PaymentMode> type = paymentModeRepository.findByMode(trans.getPaymentMode().getMode());
					if (type.isPresent()) {
						PaymentMode paymentMode = type.get();
							paymentMode.setInitial_amount(paymentMode.getInitial_amount()-t1);
							paymentModeRepository.save(paymentMode);			
						}
			   }//noted below
				else {
					Optional<PaymentMode> type = paymentModeRepository.findByMode(trans.getPaymentMode().getMode());
					if (type.isPresent()) {
						PaymentMode paymentMode = type.get();
							paymentMode.setInitial_amount(paymentMode.getInitial_amount()+t1);
							paymentModeRepository.save(paymentMode);			
						}
				 }
		} else if(transaction.getDetail().equalsIgnoreCase("income")) {
			double amount = 0.0d;
			double newAmount = transaction.getAmount();
			double oldAmount = trans.getAmount();
			if (newAmount > oldAmount) {
				amount = newAmount - oldAmount;
			} 
			else {
				amount = oldAmount - newAmount;

			}
			Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
			if (type.isPresent()) {
				PaymentMode paymentMode = type.get();
				paymentMode.setInitial_amount(paymentMode.getInitial_amount() + amount);
				paymentModeRepository.save(paymentMode);
			}
		}
		if (t.isPresent()) {
			Transaction transactionDetail = t.get();
			transactionDetail.setDescription(transaction.getDescription());
			transactionDetail.setAmount(transaction.getAmount());
			transactionDetail.setDetail(transaction.getDetail());

			Optional<Category> category = categoryRepository.findByName(transaction.getCategory().getName());

			Category cat = category.get();
			transaction.setCategory(cat);

			Optional<PaymentMode> paymentMode = paymentModeRepository
					.findByMode(transaction.getPaymentMode().getMode());

			PaymentMode pm = paymentMode.get();

			transaction.setPaymentMode(pm);

			transactionDetail.setCategory(transaction.getCategory());
			transactionDetail.setPaymentMode(transaction.getPaymentMode());

			transactionrepository.save(transactionDetail);

		} else {
			throw new TransactionNotFoundException("Transaction id not exist" + id);
		}
	}

	public void deleteTransaction(int id) {
		Optional<Transaction> transaction = transactionrepository.findById(id);
		if (transaction.isPresent()) {
			Transaction trans = transaction.get();
			long amount = trans.getAmount();
			String detail = trans.getDetail();
			PaymentMode mode = trans.getPaymentMode();
			double oldBalance = mode.getInitial_amount();
			if (detail.equals("expense")) {
				double initial_amount = oldBalance + amount;
				mode.setInitial_amount(initial_amount);
				paymentModeRepository.save(mode);
			} else {
				double initial_amount = oldBalance - amount;
				mode.setInitial_amount(initial_amount);
				paymentModeRepository.save(mode);
			}
			transactionrepository.deleteById(id);
		} else {
			throw new TransactionNotFoundException("Transaction id not exist" + id);
		}
	}

//    public List<Transaction> getTransactionWithSorting(String field){
//   	  return transactionrepository.findAll(Sort.by(Sort.Direction.DESC,field)); 
//    }
}
