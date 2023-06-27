package com.training.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.expense.model.Category;
import com.training.expense.model.CategoryNotFoundException;
import com.training.expense.service.CategoryService;


@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody Category category) throws Exception {
		categoryService.addCategory(category);
		return new ResponseEntity<>(new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategory() {
		List<Category> categories=categoryService.getCategory();
		return new ResponseEntity <List<Category>>(categories,new HttpHeaders(),HttpStatus.OK);
	}
	
	/*
	 * @PutMapping("/{id}") public HttpStatus updateCategory(@PathVariable Category
	 * category) { categoryService.updateCategory(category); return
	 * HttpStatus.FOUND; }
	 */
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteCategory(@PathVariable int id) throws CategoryNotFoundException {
		categoryService.deleteCategory(id);
		return HttpStatus.OK;
	}
}
