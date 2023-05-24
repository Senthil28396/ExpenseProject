package com.training.expense.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name="PaymentMode")
public class PaymentMode {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="account_generator")
	@SequenceGenerator(name="account_generator")
	@Column(name="id")
	private int id;
	
	@Column(name="mode")
	private String mode;
	
	@Column(name="initial_amount")
	private double initial_amount;
	
	public PaymentMode() {
		super();
	}

	public PaymentMode(int id, String mode, double initial_amount) {
		super();
		this.id = id;
		this.mode = mode;
		this.initial_amount = initial_amount;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public double getInitial_amount() {
		return initial_amount;
	}

	public void setInitial_amount(double initial_amount) {
		this.initial_amount = initial_amount;
	}
	
	
}
