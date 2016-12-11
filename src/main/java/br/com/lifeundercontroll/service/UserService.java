package br.com.lifeundercontroll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.DTO.Response.UserResponseDTO;
import br.com.lifeundercontroll.DTO.request.NewUserRequest;
import br.com.lifeundercontroll.DTO.request.UserLoginRequest;
import br.com.lifeundercontroll.DTO.request.UserUpdateRequest;
import br.com.lifeundercontroll.builders.UserBuilder;
import br.com.lifeundercontroll.entities.UserEntity;
import br.com.lifeundercontroll.exceptions.ResourceAlreadyExist;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.repository.UserRepository;
import br.com.lifeundercontroll.service.utils.Services;

@Service
public class UserService extends Services{

	@Autowired
	private UserRepository userRepository;
	
	
	public UserResponseDTO create(NewUserRequest userRequest) throws ResourceAlreadyExist{
	  Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));
	  if(user.isPresent()){
		  throw new ResourceAlreadyExist("Email já cadastrado");
	  }
	  UserEntity userEntity = UserBuilder.newUserToEntity(userRequest);
	  getUserAuthService().save(userEntity);
	  userRepository.save(userEntity);
	  return UserBuilder.entityToDTO(userEntity);
	}

	public OAuth2AccessToken login(UserLoginRequest userLogin) throws ResourceNotFound {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmailAndPassword(userLogin.getEmail(), shaPasswordEncoder.encodePassword(userLogin.getPassword(),null)));
		UserEntity userEntity = user.orElseThrow(()-> new ResourceNotFound("Usuario ou senha incorreto"));
		OAuth2AccessToken tokenAcess = getUserAuthService().getToken(userLogin.getEmail(), userLogin.getPassword());
		userEntity.setToken(tokenAcess.getValue());
		userRepository.save(userEntity);
		return tokenAcess;
	}

	public UserResponseDTO update(UserUpdateRequest userUpdate) {
		UserEntity userLogged = getRequestService().getUserLogged();
		userLogged.setName(userUpdate.getNome());
		userLogged.setSalary(userUpdate.getSalario());
		userRepository.save(userLogged);
		return UserBuilder.entityToDTO(userLogged);
	}
	
}
