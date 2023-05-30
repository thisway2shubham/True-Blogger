package com.blogapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapp.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
 
	@ExceptionHandler(ResourceNotFoundExcpetion.class)
     public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler( ResourceNotFoundExcpetion e){
		String message = e.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidExcpetion(MethodArgumentNotValidException ex)
	{
		Map<String, String> resp=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{  //from exceptions we get get binding result then all errors and then we'll traverse each error
			String fieldName=((FieldError) error).getField();
			String message=error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
}
