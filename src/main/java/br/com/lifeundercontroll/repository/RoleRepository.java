package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entities.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {

	public RoleEntity findByRole(String role);
	
}
