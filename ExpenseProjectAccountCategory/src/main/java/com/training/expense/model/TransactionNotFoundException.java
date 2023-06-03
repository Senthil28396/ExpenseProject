package com.training.expense.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {
	public TransactionNotFoundException(String s) {
	super(s);
	}
}
