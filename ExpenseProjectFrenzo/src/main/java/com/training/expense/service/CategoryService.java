package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.expense.model.Category;
import com.training.expense.model.CategoryNotFoundException;
import com.training.expense.repository.CategoryRepository;
@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public void addCategory(Category category) throws Exception {
		Optional<Category> type=categoryRepository.findByName(category.getName());
		if(type.isPresent()) {
			throw new Exception("Already exist this category");
		}
		else {
		 categoryRepository.save(category);
		}
	}

	public List<Category> getCategory() {
		List<Category> categories=(List<Category>) categoryRepository.findAll();
			return categories;
		}

		/*
		 * public void updateCategory(Category category) { Optional<Category>
		 * categories=categoryRepository.findById(category.getId()); Category
		 * newCategory=categories.get(); newCategory.setId(category.getId());
		 * newCategory.setName(category.getName());
		 * categoryRepository.save(newCategory); }
		 */
	public void deleteCategory(int id) throws CategoryNotFoundException {
		Optional<Category> category=categoryRepository.findById(id);
		if(category.isPresent())
			categoryRepository.deleteById(id);
		else
			throw new CategoryNotFoundException("No record found");
	}

	


}
