package com.cafe.spring.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
    	UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.TOKEN_HEADER);
        if (!StringUtils.isEmpty(token) && token.startsWith(JWTConstants.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = JWTConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace(JWTConstants.TOKEN_PREFIX, ""));

                String username = parsedToken
                    .getBody()
                    .getSubject();

                Collection<? extends GrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                    .get("rol"))
                	.stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

                if (!StringUtils.isEmpty(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null,  authorities);
                }
            } catch (ExpiredJwtException exception) {
                System.out.println(String.format("Request to parse expired JWT : {%s} failed : {%s}", token, exception.getMessage()));
            } catch (UnsupportedJwtException exception) {
            	 System.out.println(String.format("Request to parse unsupported JWT : {%s} failed : {%s}", token, exception.getMessage()));
            } catch (MalformedJwtException exception) {
            	 System.out.println(String.format("Request to parse invalid JWT : {%s} failed : {%s}", token, exception.getMessage()));
            } catch (IllegalArgumentException exception) {
            	 System.out.println(String.format("Request to parse empty or null JWT : {%s} failed : {%s}", token, exception.getMessage()));
            }
        }

        return null;
    }
}