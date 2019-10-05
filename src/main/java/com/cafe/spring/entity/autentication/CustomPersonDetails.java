package com.cafe.spring.entity.autentication;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.cafe.spring.entity.customer.Person;

public class CustomPersonDetails extends Person implements UserDetails {

		private Collection<? extends GrantedAuthority> authority;
	
		private static final long serialVersionUID = 1L;

		public CustomPersonDetails(final Person users) {
	        super(users);
	    }
		
		public CustomPersonDetails() {
	        super();
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {

	        return AuthorityUtils.createAuthorityList("ROLE_USER");
	        
	    }
	  
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
}
