package com.blogapp.payloads;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private long id;
	
	@NotEmpty
	@Size(min = 4,message = "Username must be min of 4 characters !!")
	private String name;
	
	@Email(message="Email address is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min=6, message = "Password must be of min 8 characters !!")
	private String password;
	
	@NotEmpty
	private String about;
}
