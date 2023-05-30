package com.blogapp.services;

import java.util.List;

import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.CategoryDto;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) throws ResourceNotFoundExcpetion;
	
	public void deleteCategory(Long categoryId) throws ResourceNotFoundExcpetion;
	
	public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundExcpetion;
	
	List<CategoryDto> getCategories();
}
