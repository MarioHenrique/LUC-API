package br.com.lifeundercontrol.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lifeundercontrol.domain.DTO.Person;
import br.com.lifeundercontrol.domain.entity.PersonEntity;
import br.com.lifeundercontrol.domain.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<Person> findAll(){
		List<Person> people = new ArrayList<>();
		
		Iterable<PersonEntity> peopleResult = personRepository.findAll();
		
		peopleResult.forEach(s->{
			people.add(modelMapper.map(s,Person.class));
		});
		
		return people;
	}
		
}
