package br.com.lifeundercontroll.service.auth;

import br.com.lifeundercontroll.entities.UserEntity;

public interface UserService {

	public UserEntity findByUsername(String username);
	
}
