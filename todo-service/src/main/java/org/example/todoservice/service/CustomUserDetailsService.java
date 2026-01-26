package org.example.todoservice.service;

import org.example.todoservice.model.User;
import org.example.todoservice.model.UserDetailsPrinciple;
import org.example.todoservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        System.out.println("Trying to load user by username: " + user.getUsername());

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsPrinciple(user);

    }
}
