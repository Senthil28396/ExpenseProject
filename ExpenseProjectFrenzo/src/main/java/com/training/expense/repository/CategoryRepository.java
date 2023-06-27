package com.training.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.expense.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

	Optional<Category> findByName(String name);

}
