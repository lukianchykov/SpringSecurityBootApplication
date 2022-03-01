package com.example.springsecuritybootapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class AuthenticationProviderImplementation implements AuthenticationProvider {

    private final UserDetailsServiceImplementation userSevice;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderImplementation(UserDetailsServiceImplementation userSevice) {
        this.userSevice = userSevice;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(userSevice == null) {
            throw new InternalAuthenticationServiceException("User service is null");
        }

        UserDetails user = userSevice.loadUserByUsername(username);

        if(user == null) {
            throw new AuthenticationCredentialsNotFoundException("No such user was found");
        }

        if(passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
        } else {
            throw new AuthenticationServiceException("Unable authenticate user due to some problems");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}