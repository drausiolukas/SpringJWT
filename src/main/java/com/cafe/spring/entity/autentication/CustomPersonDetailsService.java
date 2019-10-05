package com.cafe.spring.entity.autentication;

import com.cafe.spring.entity.autentication.CustomPersonDetails;
import com.cafe.spring.entity.customer.Person;
import com.cafe.spring.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomPersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        optionalPerson
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalPerson
                .map(CustomPersonDetails::new).get();
    }
}
