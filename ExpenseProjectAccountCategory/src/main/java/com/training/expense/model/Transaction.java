package com.training.expense.model;


import java.sql.Date;

import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_generator")
	@SequenceGenerator(name = "transaction_generator",sequenceName = "transaction_seq",allocationSize = 1)
	@Column(name="id")
	private int id;

	private String description;

	private long amount;

	private Date date;
	
	private String detail;
	
	//@JsonIgnoreProperties
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="category")
	//@MapsId
	private Category category;
	
	//@JsonIgnoreProperties
	@OneToOne(cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="payment")
	//@MapsId
	private PaymentMode paymentMode;
	
	
	public Transaction() {
		super();
	}
	
	public Transaction(int id, String description, long amount, String detail, Category category,
			PaymentMode paymentMode,Date date) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.detail = detail;
		this.category = category;
		this.paymentMode = paymentMode;
		this.date=date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
