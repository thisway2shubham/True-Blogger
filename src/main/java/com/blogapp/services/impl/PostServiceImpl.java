package com.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.Category;
import com.blogapp.Entities.Post;
import com.blogapp.Entities.User;
import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.repo.PostRepo;
import com.blogapp.repo.UserRepo;
import com.blogapp.services.PostService;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExcpetion(" User ", " UserId", userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Category ", " categoryId", categoryId));
		
		
	Post post=this.modelMapper.map(postDto, Post.class);
	post.setImageName("default.png");
	post.setAddedDate(new Date());
	post.setUser(user);
	post.setCategory(category);
	
	Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Post ", " postId ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Long postId) throws ResourceNotFoundExcpetion {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Post ", " postId ", postId));
        this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Pageable p = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy));
		Page<Post> pagePost = this.postRepo.findAll(p);
		      List<Post> posts = pagePost.getContent();
		  List<PostDto> list = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		  PostResponse postResponse=new PostResponse();
		  
		  postResponse.setContent(list);
		  postResponse.setPageNumber(pagePost.getNumber());
		  postResponse.setPageSize(pagePost.getSize());
		  postResponse.setTotalElements(pagePost.getTotalElements());
		  postResponse.setTotalPages(pagePost.getTotalPages());
		  postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Post ", " postId ", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExcpetion(" Category ", " categoryId ", categoryId));
	    List<Post> posts=this.postRepo.findByCategory(category);
        List<PostDto>PD=posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return PD;
	}

	@Override
	public List<PostDto> getPostsByUser(Long userId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		User users = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExcpetion(" User "," userId ", userId));
		List<Post> posts=this.postRepo.findByUser(users);
	    List<PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	    return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		// TODO Auto-generated method stub
		List<Post> list = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> list2 = list.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return list2;
	}

}
