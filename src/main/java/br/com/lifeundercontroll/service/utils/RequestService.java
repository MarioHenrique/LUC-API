package br.com.lifeundercontroll.service.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.entities.UserEntity;
import br.com.lifeundercontroll.repository.UserRepository;

@Service
public class RequestService {

	@Autowired
	private HttpServletRequest httpRequest;
	
	@Autowired
	private UserRepository userRepository;
	
	public String getHost(){
		return "http://"+httpRequest.getLocalName()+":"+httpRequest.getLocalPort();
	}
	
	public UserEntity getUserLogged(){
		String token = httpRequest.getHeader("token");
		return userRepository.findByToken(token);
	}
	
}
