package com.blogapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Category;
import com.blogapp.Entities.Post;
import com.blogapp.Entities.User;

public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
