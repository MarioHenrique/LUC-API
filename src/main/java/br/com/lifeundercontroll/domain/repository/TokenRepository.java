package br.com.lifeundercontroll.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lifeundercontroll.domain.entity.TokenEntity;

public interface TokenRepository extends CrudRepository<TokenEntity,Long>{

	public TokenEntity findByUserNameAndPasswordAndActiveTrue(String username,String password);
	public TokenEntity findByTokenAndActiveTrue(String token);
	
}
