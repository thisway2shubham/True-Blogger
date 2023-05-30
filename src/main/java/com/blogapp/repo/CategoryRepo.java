package com.blogapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
