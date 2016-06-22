package br.com.lifeundercontroll.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.Dto.Response.BillResponse;
import br.com.lifeundercontroll.Dto.Response.UserResponse;
import br.com.lifeundercontroll.Dto.request.UserRequest;
import br.com.lifeundercontroll.builders.BillResponseBuilder;
import br.com.lifeundercontroll.builders.UserEntityBuilder;
import br.com.lifeundercontroll.builders.UserResponseBuilder;
import br.com.lifeundercontroll.entity.UserEntity;
import br.com.lifeundercontroll.exceptions.ResourceAlreadyExist;
import br.com.lifeundercontroll.exceptions.ResourceNotFound;
import br.com.lifeundercontroll.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public void createUser(UserRequest userRequest) throws ResourceAlreadyExist{
	  Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));
	  if(user.isPresent()){
		  throw new ResourceAlreadyExist("Email já cadastrado");
	  }
	  UserEntity userEntity = UserEntityBuilder.build(userRequest);	
	  userRepository.save(userEntity);
	}

	public UserResponse login(String email, String password) throws ResourceNotFound {
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmailAndPassword(email, shaPasswordEncoder.encodePassword(password,null)));
		UserEntity userReturned = user.orElseThrow(()-> new ResourceNotFound("Usuario ou senha incorreto"));
		return UserResponseBuilder.build(userReturned);
	}

	public List<BillResponse> getBills(String userToken) throws ResourceNotFound {
		Optional<UserEntity> user = Optional.ofNullable(userRepository.findByToken(userToken));
		UserEntity userEntity = user.orElseThrow(()->new ResourceNotFound("Usuario não encontrado"));
		return userEntity.getBills().stream().map(s-> BillResponseBuilder.build(s)).collect(Collectors.toList());
	}
	
}
