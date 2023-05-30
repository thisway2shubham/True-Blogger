package com.blogapp.controllers;

import java.io.IOException;
import java.util.List;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.config.AppConstants;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId) throws ResourceNotFoundExcpetion{
		PostDto addedPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(addedPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Long userId) throws ResourceNotFoundExcpetion{
	List<PostDto> posts=this.postService.getPostsByUser(userId);
	return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long categoryId) throws ResourceNotFoundExcpetion{
		List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost( @PathVariable Long postId) throws ResourceNotFoundExcpetion {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is deleted successfully!!",true);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) throws ResourceNotFoundExcpetion{
	PostDto updatePost=this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
 	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId", required = false) String sortBy
			){
		PostResponse postResponse=this.postService.getAllPosts(pageNumber, pageSize, sortBy);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDto> posts = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostimage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Long postId
			) throws IOException, ResourceNotFoundExcpetion{
		String fileName = this.fileService.uploadImage(path, image);
		PostDto postDto = this.postService.getPostById(postId);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
}
