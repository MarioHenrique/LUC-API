package br.com.lifeundercontroll.security;

import br.com.lifeundercontroll.entity.UserApiEntity;

public interface UserApiService {

	public UserApiEntity findByUsername(String username);
	
}
