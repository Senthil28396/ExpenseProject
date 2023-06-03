package com.training.expense.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.PaymentMode;
@Repository
public interface PaymentModeRepository extends CrudRepository<PaymentMode, Integer> {
	Optional<PaymentMode> findByMode(String l);
}
