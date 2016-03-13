package br.com.lifeundercontrol.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontrol.domain.DTO.Person;
import br.com.lifeundercontrol.domain.service.PersonService;

@RestController
@RequestMapping(value="/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public @ResponseBody List<Person> getPeople(){		
		return personService.findAll();
	}
	
}
