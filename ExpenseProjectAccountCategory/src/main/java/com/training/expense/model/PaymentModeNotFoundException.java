package com.training.expense.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PaymentModeNotFoundException extends RuntimeException {


		public PaymentModeNotFoundException(String exception) {
			super(exception);
		}
		
		public PaymentModeNotFoundException(String exception,Throwable t) {
			super(exception,t);
		}
	}