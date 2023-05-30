package com.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.Category;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.services.CategoryService;

import net.bytebuddy.asm.Advice.This;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		    Category category=this.modelMapper.map(categoryDto, Category.class);
		    Category addedCat=this.categoryRepo.save(category);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundExcpetion(" Category ", " Catgeory Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
	Category updatedCategory =	this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
	    Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Category ", " CategoryId ", categoryId));
	  this.categoryRepo.delete(cat);	
	}

	@Override
	public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
	Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Category ", " CategoryId", categoryId));
		return this.modelMapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories=this.categoryRepo.findAll();
	List<CategoryDto> catDtos =categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
	     
		return catDtos;
	}

}
