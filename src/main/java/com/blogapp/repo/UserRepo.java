package com.blogapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.Entities.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
