package br.com.lifeundercontroll.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public TokenNotFoundException(String mensagem){
		super(mensagem);
	}
	
}
