package com.cafe.spring.configuration;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cafe.spring.entity.customer.Person;
import com.cafe.spring.repository.PersonRepository;


@Configuration
@Profile("test")
public class TestConfiguration  implements CommandLineRunner{

	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(String... args) throws Exception {
		Person u1 = new Person(null, "maria@gmail.com", "123456","Maria Brown","Kim",1 );
		Person u2 = new Person(null,  "alex@gmail.com", "$2a$10$cHu9a2dG6UVlfsPu.vjKwOowC.TZEbt65zTefWMwF47u8wuubdC5.","Alex Green", "Silva",1 );
		personRepository.saveAll(Arrays.asList(u1,u2));
	}
	
	
}
