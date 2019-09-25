package com.cafe.spring.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.spring.entities.User;
/**
 * 
 * @author Drausio
 * @since 20-09-2019
 * @apiNote nada
 *
 */
@RestController
@RequestMapping(value="/users/private")
public class UserEndpoints {
	/**
	 * @see Este endpoint mostra todos os clientes
	 * @param null 
	 * @return User
	 */
	@GetMapping
	public ResponseEntity<User> find(){
		User user = new User(1, "drausio", "drausioo@gmail.com", "123");
		return ResponseEntity.ok().body(user);
	}

}
