package com.market.api.domain.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {
//    Esto es un ejemplo, en realidad esto deberia recibir o apuntar a una DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("gerson", "{noop}gerson123", new ArrayList<>());
    }
}
