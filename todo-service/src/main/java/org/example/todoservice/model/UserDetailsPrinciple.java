package org.example.todoservice.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Nullable;


public class UserDetailsPrinciple implements UserDetails{

    private final User user;

    public UserDetailsPrinciple(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public @Nullable String getUsername() {
        return user.getUsername();
    }
}
