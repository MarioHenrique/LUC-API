package br.com.lifeundercontroll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExist extends Exception{

	public ResourceAlreadyExist(String message){
		super(message);
	}
	
}
