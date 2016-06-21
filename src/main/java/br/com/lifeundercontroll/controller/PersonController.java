package br.com.lifeundercontroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.DTO.Person;
import br.com.lifeundercontroll.service.PersonService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@ApiOperation(value="Lista todas as pessoas do sistema")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@PreAuthorize("hasAuthority('getPeople')")
	public List<Person> getPeople(){		
		return personService.findAll();
	}
	
}
