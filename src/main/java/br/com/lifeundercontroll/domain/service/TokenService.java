package br.com.lifeundercontroll.domain.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lifeundercontroll.domain.DTO.Token;
import br.com.lifeundercontroll.domain.entity.TokenEntity;
import br.com.lifeundercontroll.domain.exceptions.TokenNotFoundException;
import br.com.lifeundercontroll.domain.repository.TokenRepository;

@Component
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger = Logger.getLogger(TokenService.class);
	
	public String getByUserAndPassword(String username,String password) throws TokenNotFoundException{
		Optional<TokenEntity> tokenOptional = Optional.ofNullable(tokenRepository.findByUserNameAndPasswordAndActiveTrue(username, password));
		try{
			JSONObject response = new JSONObject();
			response.put("token", tokenOptional.map(t->t.getToken()).get());
			return response.toString();
		}catch(NoSuchElementException e){
			logger.error(e);
			throw new TokenNotFoundException("Token not found for this username and password");
		}
	}
	
	public Token requestAccess(String userName,String password){
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setActive(false);
		tokenEntity.setUserName(userName);
		tokenEntity.setPassword(password);
		tokenRepository.save(tokenEntity);
		return modelMapper.map(tokenEntity,Token.class);
	}
	
	public void updateToken(Long id) throws TokenNotFoundException{
			Optional<TokenEntity> tokenOptional = Optional.ofNullable(tokenRepository.findOne(id));
			tokenOptional.ifPresent(s->{
				String key = new BigInteger(130,new SecureRandom()).toString(32);
				StringTokenizer token = new StringTokenizer(key);
				s.setActive(true);
				s.setToken(token.nextToken());
				tokenRepository.save(s);
			});
			tokenOptional.orElseThrow(()-> new TokenNotFoundException("Token not found for this id"));
	}
	
	public Boolean findByToken(String token){
		Optional<TokenEntity> TokenOptional = Optional.ofNullable(tokenRepository.findByTokenAndActiveTrue(token));
		if(TokenOptional.isPresent()){
			return false;
		}
		return true;
	}
	
}
