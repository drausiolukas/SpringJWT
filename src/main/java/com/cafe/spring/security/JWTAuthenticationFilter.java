package com.cafe.spring.security;


import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cafe.spring.entity.autentication.CustomPersonDetails;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(JWTConstants.AUTH_LOGIN_URL);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	CustomPersonDetails account = null;
		try {
			account = parseLoginData(request);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} 
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws java.io.IOException {
    	CustomPersonDetails user = (CustomPersonDetails) authentication.getPrincipal();

        List<String> roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        byte[] signingKey = JWTConstants.JWT_SECRET.getBytes();

       String token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", JWTConstants.TOKEN_TYPE)
            .setIssuer(JWTConstants.TOKEN_ISSUER)
            .setAudience(JWTConstants.TOKEN_AUDIENCE)
            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            .claim("rol", roles)
            .compact();

     
       response.addHeader(JWTConstants.TOKEN_HEADER, JWTConstants.TOKEN_PREFIX + token);
       response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(
               "{\"" + JWTConstants.TOKEN_HEADER + "\":\"" + JWTConstants.TOKEN_PREFIX+token + "\"}"
       );
        
    }
    
    private CustomPersonDetails parseLoginData(HttpServletRequest request) throws JsonMappingException, java.io.IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(request.getInputStream(), CustomPersonDetails.class);
        } catch (IOException exception) {
            // Return empty "invalid" login data
            return new CustomPersonDetails();
        }
    }
}


