package com.training.expense.repository;

import org.springframework.data.repository.CrudRepository;

import com.training.expense.model.Category;

public interface CategoryRepository extends CrudRepository<Category,Integer > {

}
