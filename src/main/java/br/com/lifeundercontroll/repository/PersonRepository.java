package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entity.PersonEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity,Long> {

	
}
