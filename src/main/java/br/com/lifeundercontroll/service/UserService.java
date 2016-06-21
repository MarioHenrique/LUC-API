package br.com.lifeundercontroll.service;

import br.com.lifeundercontroll.entity.UserEntity;

public interface UserService {

	public UserEntity findByUsername(String username);
	
}
