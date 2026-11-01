package com.thunder.journalApplication.services;

import com.thunder.journalApplication.entity.user;
import com.thunder.journalApplication.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServicesIMPL implements UserDetailsService {

    @Autowired
    private userRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user User = UserRepository.findByUserName(username);
        if(User != null){

            return org.springframework.security.core.userdetails.User.builder()
                    .username(User.getUserName())
                    .password(User.getPassword())
                    .roles(User.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("Username not found" + username);
    }
}
