package br.com.lifeundercontroll.builders;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import br.com.lifeundercontroll.Dto.request.UserRequest;
import br.com.lifeundercontroll.entity.UserEntity;

public class UserEntityBuilder {

	public static UserEntity build(UserRequest request) {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(request.getEmail());
		userEntity.setName(request.getName());
		userEntity.setPassword(shaPasswordEncoder.encodePassword(request.getPassword(),null));
		userEntity.setSalary(request.getSalary());
		userEntity.setToken(shaPasswordEncoder.encodePassword(request.getEmail()+request.getEmail()+request.getPassword(),null));
		return userEntity;
	}
	
}
