package com.cafe.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.spring.entity.customer.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
	 Optional<Person> findByUsername(String username);

}
