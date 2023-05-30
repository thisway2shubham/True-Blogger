package com.blogapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
