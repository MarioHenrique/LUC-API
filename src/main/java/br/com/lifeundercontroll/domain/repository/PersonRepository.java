package br.com.lifeundercontroll.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.domain.entity.PersonEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity,Long> {

	
}
