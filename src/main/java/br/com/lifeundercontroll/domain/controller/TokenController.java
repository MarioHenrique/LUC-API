package br.com.lifeundercontroll.domain.controller;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.domain.DTO.Token;
import br.com.lifeundercontroll.domain.exceptions.TokenNotFoundException;
import br.com.lifeundercontroll.domain.service.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/token")
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@ApiOperation(value="Busca token pelo username e password")
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody String getToken(
			@ApiParam(value="username",required=true)
			@RequestParam String username,
			@ApiParam(value="password",required=true)
			@RequestParam String password
			) throws TokenNotFoundException{
		return tokenService.getByUserAndPassword(username, password);
	}

	@ApiOperation(value="Solicita acesso a api")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Token> solicitarUsuario(
			@ApiParam(name="userName",required=true) @RequestParam String userName,
			@ApiParam(name="password",required=true) @RequestParam String password){

		return new ResponseEntity<Token>(tokenService.requestAccess(userName,password),HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="Valida um usuario")
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity validarUmUsuario(
			@ApiParam(value="tokenId") @RequestParam Long tokenId) throws TokenNotFoundException{
		
		tokenService.updateToken(tokenId);
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
}
