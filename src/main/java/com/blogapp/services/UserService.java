package com.blogapp.services;

import java.util.List;

import com.blogapp.exceptions.ResourceNotFoundExcpetion;
import com.blogapp.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, Long userId) throws ResourceNotFoundExcpetion;
	
	UserDto getUserById(Long userId) throws ResourceNotFoundExcpetion;
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userId) throws ResourceNotFoundExcpetion;	
}
