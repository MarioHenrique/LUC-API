package br.com.lifeundercontroll.service;

import br.com.lifeundercontroll.entity.UserApiEntity;

public interface UserApiService {

	public UserApiEntity findByUsername(String username);
	
}
