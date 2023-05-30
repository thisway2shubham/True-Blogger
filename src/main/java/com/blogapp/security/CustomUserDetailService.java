package com.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.blogapp.Entities.User;

import com.blogapp.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		// TODO Auto-generated method stub
		     User user=this.userRepo.findByEmail(username).get();
		
		return user;
	}

	
}
