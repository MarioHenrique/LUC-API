package br.com.lifeundercontroll.controller.utils;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(UnauthorizedClientException.class)
	public void unauthorizedClientExceptionHandler(HttpServletResponse response,UnauthorizedClientException e) throws IOException{
		response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public void invalidParameterException(HttpServletResponse response,InvalidParameterException e) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
	}
	
}
