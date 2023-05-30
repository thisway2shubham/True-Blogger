package com.blogapp.services;

import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, long postId) throws ResourceNotFoundExcpetion;
	
	void deleteComment(Long commentId) throws ResourceNotFoundExcpetion;
}
