package br.com.lifeundercontrol.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontrol.domain.entity.PersonEntity;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity,Long> {

	
}
