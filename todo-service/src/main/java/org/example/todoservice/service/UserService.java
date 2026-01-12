package org.example.todoservice.service;

import org.example.todoservice.repository.TaskRepo;
import org.example.todoservice.repository.UserRepo;
import org.springframework.stereotype.Service;



@Service
public class UserService{
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    public UserService(UserRepo userRepo, TaskRepo taskRepo){
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }
}