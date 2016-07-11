package br.com.lifeundercontroll.builders;

import br.com.lifeundercontroll.dto.response.UserResponse;
import br.com.lifeundercontroll.entity.UserEntity;

public class UserResponseBuilder {

	public static UserResponse build(UserEntity userEntity){
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(userEntity.getEmail());
		userResponse.setName(userEntity.getName());
		userResponse.setSalary(userEntity.getSalary());
		userResponse.setToken(userEntity.getToken());
		return userResponse;
	}
	
}
