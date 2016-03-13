package br.com.lifeundercontroll.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lifeundercontroll.domain.DTO.Person;
import br.com.lifeundercontroll.domain.service.PersonService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@ApiOperation(value="Lista todas as pessoas do sistema")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public @ResponseBody List<Person> getPeople(){		
		return personService.findAll();
	}
	
}
