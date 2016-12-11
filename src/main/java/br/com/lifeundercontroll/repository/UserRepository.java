package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findByEmailAndPassword(String email,String password);
	
	public UserEntity findByEmail(String email);
	
	public UserEntity findByToken(String token);
	
}
