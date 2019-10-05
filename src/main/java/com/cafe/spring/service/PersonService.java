package com.cafe.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.spring.entity.customer.Person;
import com.cafe.spring.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository userRepository;
	
	public List<Person> findAll(){
		return userRepository.findAll();
	}
	
	public Person find(Long id){
		return userRepository.findById(id).get();
	}

}
