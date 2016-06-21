package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entity.TokenEntity;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity,Long>{

	public TokenEntity findByUserNameAndPasswordAndActiveTrue(String username,String password);
	public TokenEntity findByToken(String token);
	
}
