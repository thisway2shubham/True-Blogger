package com.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.Comment;
import com.blogapp.Entities.Post;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repo.CommentRepo;
import com.blogapp.repo.PostRepo;
import com.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Post ", " postId ", postId));
		
		Comment comment= this.modelMapper.map(commentDto,Comment.class);
		
		comment.setPost(post);
		
	    Comment savedComment=this.commentRepo.save(comment);
	    
	    return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub

		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Comment ", " commentID ", commentId));
		this.commentRepo.delete(com);
	}

}
