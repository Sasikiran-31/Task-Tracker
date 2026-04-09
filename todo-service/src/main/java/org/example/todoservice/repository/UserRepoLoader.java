package org.example.todoservice.repository;

import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.User;
import org.example.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRepoLoader implements CommandLineRunner {

    @Autowired
    private final UserService userService;

    
    public UserRepoLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userService.userExists("admin")){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.setROLE("ADMIN");
            userService.save(admin);
        }

    }
}
