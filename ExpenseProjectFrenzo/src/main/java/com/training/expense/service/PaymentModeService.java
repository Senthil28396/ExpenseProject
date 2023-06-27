package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.expense.model.PaymentMode;
import com.training.expense.model.PaymentModeNotFoundException;
import com.training.expense.model.Category;
import com.training.expense.repository.PaymentModeRepository;
@Service
public class PaymentModeService {
	@Autowired
	PaymentModeRepository paymentRepository;

	public void addAccount(PaymentMode account) throws Exception {
		Optional<PaymentMode> type=paymentRepository.findByMode(account.getMode());
		if(type.isPresent()) {
			throw new Exception("Already exist this mode");
		}
		else {
			paymentRepository.save(account);
		}
	}

	public List<PaymentMode> getAccount() {
		Iterable<PaymentMode> accounts= paymentRepository.findAll();
		return (List<PaymentMode>) accounts;
	}

	public void updateAccount(PaymentMode account,int id) {
		Optional<PaymentMode> accounts=paymentRepository.findById(id);
		PaymentMode newAccount=accounts.get();
		newAccount.setMode(account.getMode());
		newAccount.setInitial_amount(account.getInitial_amount());
		paymentRepository.save(newAccount);
	}

	public void deleteAccount(int id) throws PaymentModeNotFoundException {
		Optional<PaymentMode> account=paymentRepository.findById(id);
		if(account.isPresent())
			paymentRepository.deleteById(id);
		else
			throw new PaymentModeNotFoundException("No record found");
	}

}
