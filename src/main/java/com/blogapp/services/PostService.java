package com.blogapp.services;

import java.util.List;

import com.blogapp.Entities.Post;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Long userId, Long categoryId) throws ResourceNotFoundExcpetion;

	PostDto updatePost(PostDto postDto, Long postId) throws ResourceNotFoundExcpetion;

	void deletePost(Long postId) throws ResourceNotFoundExcpetion;

	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

	PostDto getPostById(Long postId) throws ResourceNotFoundExcpetion;

	List<PostDto> getPostsByCategory(Long categoryId) throws ResourceNotFoundExcpetion;

	List<PostDto> getPostsByUser(Long userId) throws ResourceNotFoundExcpetion;

	List<PostDto> searchPost(String keyword);
}
