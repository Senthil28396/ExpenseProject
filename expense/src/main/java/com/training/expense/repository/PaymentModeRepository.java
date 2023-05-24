package com.training.expense.repository;

import org.springframework.data.repository.CrudRepository;

import com.training.expense.model.PaymentMode;

public interface PaymentModeRepository extends CrudRepository<PaymentMode, Integer> {

}
