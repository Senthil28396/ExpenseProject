package com.training.expense.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator="category_generator")
	@SequenceGenerator(name="category_generator",sequenceName = "category_seq",allocationSize = 1)	
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToOne(mappedBy="category")
	private Transaction transaction;
	
	public Category() {
		super();
	}

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
