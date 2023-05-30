package com.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.Entities.Category;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;

import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto categorydto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) throws ResourceNotFoundExcpetion{
		CategoryDto categoryDto2=this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) throws ResourceNotFoundExcpetion{
	  CategoryDto categoryDto3=this.categoryService.getCategory(categoryId);
	  return new ResponseEntity<CategoryDto>(categoryDto3,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getCategories(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) throws ResourceNotFoundExcpetion{
		this.categoryService.deleteCategory(categoryId);
	     return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully !!",true),HttpStatus.OK);
	}
}
