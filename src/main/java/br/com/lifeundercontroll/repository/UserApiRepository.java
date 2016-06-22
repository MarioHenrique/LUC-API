package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.lifeundercontroll.entity.UserApiEntity;

@Repository
public interface UserApiRepository extends CrudRepository<UserApiEntity, Long>{

	UserApiEntity findByUsername(String username);
	
}
