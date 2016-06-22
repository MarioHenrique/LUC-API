package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findByEmailAndPassword(String email,String password);
	
}
