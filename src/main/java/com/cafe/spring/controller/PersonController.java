package com.cafe.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.spring.entity.customer.Person;
import com.cafe.spring.service.PersonService;
/**
 * 
 * @author Drausio
 * @since 20-09-2019
 * @apiNote nada
 *
 */
@RestController
@RequestMapping(value="/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	/**
	 * @see Este endpoint mostra todos os clientes
	 * @param null 
	 * @return User
	 */
	@GetMapping
	public ResponseEntity<List<Person>> findAll(){
		List<Person> personList = personService.findAll();
		return ResponseEntity.ok().body(personList);
	}

	@GetMapping
	@RequestMapping(method = RequestMethod.GET,value = "/{id}")
	public ResponseEntity<Person> find(@PathVariable Long id){
		Person person = personService.find(id);
		return ResponseEntity.ok().body(person);
	}

}
