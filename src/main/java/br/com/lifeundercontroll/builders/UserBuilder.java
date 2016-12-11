package br.com.lifeundercontroll.builders;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import br.com.lifeundercontroll.DTO.Response.UserResponseDTO;
import br.com.lifeundercontroll.DTO.request.NewUserRequest;
import br.com.lifeundercontroll.entities.UserEntity;

public class UserBuilder {

	public static UserResponseDTO entityToDTO(UserEntity entity){
		UserResponseDTO userResponse = new UserResponseDTO();
		userResponse.setEmail(entity.getEmail());
		userResponse.setName(entity.getName());
		userResponse.setSalary(entity.getSalary());
		return userResponse;
	}
	
	public static UserEntity newUserToEntity(NewUserRequest userDTO){
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setName(userDTO.getName());
		userEntity.setPassword(shaPasswordEncoder.encodePassword(userDTO.getPassword(),null));
		userEntity.setSalary(userDTO.getSalary());
		return userEntity;
	}
	
}
