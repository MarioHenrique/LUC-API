package br.com.lifeundercontroll.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(UnauthorizedClientException.class)
	public void ununauthorizedClientHandler(HttpServletResponse response,UnauthorizedClientException e) throws IOException{
		response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}
	
}
