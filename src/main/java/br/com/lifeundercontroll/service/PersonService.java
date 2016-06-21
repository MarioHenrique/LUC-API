package br.com.lifeundercontroll.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.DTO.Person;
import br.com.lifeundercontroll.entity.PersonEntity;
import br.com.lifeundercontroll.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	Logger logger = Logger.getLogger(PersonService.class);
	
	public List<Person> findAll(){

		List<Person> people = new ArrayList<>();
		
		Iterable<PersonEntity> peopleResult = personRepository.findAll();
		
		peopleResult.forEach(s->{
			people.add(modelMapper.map(s,Person.class));
		});
		
		return people;
	}
		
}
