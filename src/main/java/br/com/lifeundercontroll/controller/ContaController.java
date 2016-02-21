package br.com.lifeundercontroll.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContaController {

	@RequestMapping(value="/conta",method=RequestMethod.GET)
	public String conta(){
		return "Conta";
	}
	
}
