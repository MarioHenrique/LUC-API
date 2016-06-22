package br.com.lifeundercontroll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lifeundercontroll.entity.LogEntity;

@Repository
public interface LogRepository extends CrudRepository<LogEntity,Long>{

	
}