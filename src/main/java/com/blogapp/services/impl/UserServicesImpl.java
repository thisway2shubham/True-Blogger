package com.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.exceptions.*;

import com.blogapp.Entities.User;
import com.blogapp.payloads.UserDto;
import com.blogapp.repo.UserRepo;
import com.blogapp.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Service
public class UserServicesImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		
		return UserToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto,Long userId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExcpetion("User","id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=this.userRepo.save(user);
		return UserToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Long userId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExcpetion("User","id", userId));
		return UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users=this.userRepo.findAll();
		
		 List<UserDto> userDtos = users.stream().map(user -> this.UserToDto(user)).collect(Collectors.toList());
		 
		 return userDtos;
	}

	@Override
	public void deleteUser(Long userId) throws ResourceNotFoundExcpetion {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExcpetion("User","id", userId));
		this.userRepo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	public UserDto UserToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
