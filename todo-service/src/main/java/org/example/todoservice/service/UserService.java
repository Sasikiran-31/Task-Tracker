package org.example.todoservice.service;

import jakarta.transaction.Transactional;
import org.example.todoservice.DTOS.Notification_Dto;
import org.example.todoservice.DTOS.TaskDTO;
import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.Task;
import org.example.todoservice.model.User;
import org.example.todoservice.repository.TaskRepo;
import org.example.todoservice.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final RestTemplate restTemplate;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final String NOTIFICATION_URL = "http://localhost:8082/notifications";

    public UserService(UserRepo userRepo, TaskRepo taskRepo,  RestTemplate restTemplate) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.restTemplate = restTemplate;
    }


    public UserDTO save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return new UserDTO(user.getUsername(), user.getUserId());
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public UserDTO addTaskById(int userId, Task task) {
            User user = userRepo.findById(userId)
                    .orElseThrow();
                user.addTask(task);
                taskRepo.save(task);
                Notification_Dto payload = new Notification_Dto(task.getDescription(), userId);

                try{
                    restTemplate.postForEntity(NOTIFICATION_URL, payload, Void.class);
                } catch (HttpClientErrorException e) {
                    System.err.println("Failed to send notification");
                }
                return new UserDTO(user.getUsername(), user.getUserId());

    }

    public List<TaskDTO> findall(int userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<Task> tasks = user.getTasks();
        return tasks.stream().map(task -> new TaskDTO(task.getTaskId(), task.getDescription())).collect(Collectors.toList());
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> new UserDTO(user.getUsername(), user.getUserId())).collect(Collectors.toList());

    }

    public void clearAll() {
        List<User> users = userRepo.findAll();

        for (User user : users) {
            if(!user.getUsername().equals("admin")){
                userRepo.deleteUserByUsername(user.getUsername());
            }
        }
    }

}