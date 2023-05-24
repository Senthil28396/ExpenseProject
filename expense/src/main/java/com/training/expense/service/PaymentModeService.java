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

	public void addAccount(PaymentMode account) {
		paymentRepository.save(account);
	}

	public List<PaymentMode> getAccount() {
		Iterable<PaymentMode> accounts= paymentRepository.findAll();
		return (List<PaymentMode>) accounts;
	}

	public void updateAccount(PaymentMode account) {
		Optional<PaymentMode> accounts=paymentRepository.findById(account.getId());
		PaymentMode newAccount=accounts.get();
		newAccount.setId(account.getId());
		newAccount.setMode(account.getMode());
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
